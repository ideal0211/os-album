package club.xyes.os.album.service.general.impl;

import club.xyes.os.album.commons.entity.Album;
import club.xyes.os.album.commons.exception.BadRequestException;
import club.xyes.os.album.commons.exception.InternalServerErrorException;
import club.xyes.os.album.commons.util.MultipartFileUtil;
import club.xyes.os.album.commons.util.TextUtils;
import club.xyes.os.album.repository.AbstractMapper;
import club.xyes.os.album.repository.mapper.AlbumMapper;
import club.xyes.os.album.service.basic.impl.AbstractServiceImpl;
import club.xyes.os.album.service.conf.ApplicationConfiguration;
import club.xyes.os.album.service.general.AlbumService;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Create by 郭文梁 2019/11/22 17:50
 * AlbumServiceImpl
 * 相册相关业务行为定义
 *
 * @author 郭文梁
 * @data 2019/11/22 17:50
 */
@Slf4j
@Service
public class AlbumServiceImpl extends AbstractServiceImpl<Album> implements AlbumService {
    private final AlbumMapper albumMapper;
    private final ApplicationConfiguration applicationConfiguration;

    public AlbumServiceImpl(AbstractMapper<Album> mapper, AlbumMapper albumMapper, ApplicationConfiguration applicationConfiguration) {
        super(mapper);
        this.albumMapper = albumMapper;
        this.applicationConfiguration = applicationConfiguration;
    }

    @Override
    public <T extends Album> void resolvePath(T e) {
        val root = applicationConfiguration.getCdnServer();
        if (TextUtils.isTrimedNotEmpty(e.getCovers())) {
            e.setCovers(root + applicationConfiguration.getAlbumCoversPath() + e.getCovers());
        }
        val images = getImageList(e);
        val fullPathImages = images.stream()
                .filter(TextUtils::isTrimedNotEmpty)
                .map(s -> root + applicationConfiguration.getAlbumImagesPath() + s)
                .collect(Collectors.toList());
        e.setImages(fullPathImages);
    }

    @Override
    public void setCovers(Album album, MultipartFile file) {
        val path = applicationConfiguration.getWorkspace() + applicationConfiguration.getAlbumCoversPath();
        try {
            val filename = MultipartFileUtil.save(file, path);
            album.setCovers(filename);
            updateById(album);
        } catch (IOException e) {
            throw new InternalServerErrorException("Error On Save Covers file!", e);
        }
    }

    @Override
    public void appendImage(Album album, MultipartFile file) {
        val path = applicationConfiguration.getWorkspace() + applicationConfiguration.getAlbumImagesPath();
        try {
            val filename = MultipartFileUtil.save(file, path);
            val images = getImageList(album);
            images.add(filename);
            album.setImageJson(JSON.toJSONString(images));
            updateById(album);
        } catch (IOException e) {
            throw new InternalServerErrorException("Error On Save Album image!", e);
        }
    }

    @Override
    public void deleteImage(Album album, Integer index) {
        val images = getImageList(album);
        if (images.size() <= index || index < 0) {
            throw new BadRequestException("无效的图片索引！" + index);
        }
        images.remove((int) index);
        album.setImageJson(JSON.toJSONString(images));
        updateById(album);
    }

    /**
     * Get Image List
     *
     * @param album album
     * @return image list
     */
    private List<String> getImageList(Album album) {
        if (TextUtils.isTrimedEmpty(album.getImageJson())) {
            return new ArrayList<>();
        }
        val images = JSON.parseArray(album.getImageJson(), String.class);
        return images == null ? new ArrayList<>() : images;
    }

    @Override
    public void moveCategory(Integer oldCategory, int newCategory) {
        val rows = albumMapper.updateCategoryIdByCategoryId(oldCategory, newCategory);
        log.info("Move Category[{}] albums to category[{}], res=[{}]!", oldCategory, newCategory, rows);
    }

    @Override
    public List<Album> findByCategory(Integer categoryId) {
        return albumMapper.selectByCategory(categoryId);
    }

    @Override
    public List<Album> newest(Integer max) {
        return albumMapper.selectAllOrderByCreateTimeDesc(max);
    }

    @Override
    public PageInfo<Album> list(int page, int rows) {
        return PageHelper.startPage(page, rows, "create_time desc")
                .doSelectPageInfo(this::all);
    }

    @Override
    public Date maxUpdateTime() {
        return albumMapper.maxUpdateTime();
    }

    @Override
    public PageInfo<Album> homeAlbums(Integer page, Integer rows, String title) {
        PageHelper.startPage(page, rows);
        List<Album> list = albumMapper.selectHomeAlbums(title);
        return new PageInfo<>(list);
//        return PageHelper.startPage(page, rows).doSelectPageInfo(albumMapper::selectHomeAlbums);
    }
}

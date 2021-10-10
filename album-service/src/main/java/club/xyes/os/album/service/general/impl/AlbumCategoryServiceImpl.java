package club.xyes.os.album.service.general.impl;

import club.xyes.os.album.commons.entity.AlbumCategory;
import club.xyes.os.album.repository.mapper.AlbumCategoryMapper;
import club.xyes.os.album.service.basic.impl.AbstractServiceImpl;
import club.xyes.os.album.service.general.AlbumCategoryService;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Create by 郭文梁 2019/11/21 18:20
 * AlbumCategoryServiceImpl
 * 相册分类相关业务行为实现
 *
 * @author 郭文梁
 * @data 2019/11/21 18:20
 */
@Service
public class AlbumCategoryServiceImpl extends AbstractServiceImpl<AlbumCategory> implements AlbumCategoryService {
    private final AlbumCategoryMapper albumCategoryMapper;

    public AlbumCategoryServiceImpl(AlbumCategoryMapper albumCategoryMapper) {
        super(albumCategoryMapper);
        this.albumCategoryMapper = albumCategoryMapper;
    }

    @Override
    public List<AlbumCategory> all() {
        val query = new AlbumCategory();
        query.setDeleted(false);
        return albumCategoryMapper.select(query);
    }
}

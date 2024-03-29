package com.hfut.newsbackend.controller;

import com.hfut.newsbackend.response.ResponseResult;
import com.hfut.newsbackend.service.impl.NewsServiceImpl;
import com.hfut.newsbackend.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 针对新闻的一些接口，比如获取推荐新闻什么的
 */
@RestController
@CrossOrigin
public class NewsController {

    @Autowired
    private NewsServiceImpl newsService ;

    /**
     * TODO 用户未登录状态下返回的新闻
     * @param id
     * @param refresh_count
     * @param pageSize
     * @return
     */
    @GetMapping("/getRecNews/{id}/{refresh_count}/{pageSize}")
    @ApiOperation("返回前端不同分类情况下的新闻 ,identity就是分类")
    public JSONResult getRecNews(@PathVariable Integer id, @PathVariable Integer refresh_count,  @PathVariable Integer pageSize) {
        return JSONResult.ok(newsService.getRecNews(id , refresh_count, pageSize)) ;
    }

    /**
     * TODO 用户登录之后根据浏览历史来推荐新闻
     */
    @GetMapping("/getRecNewsByHistory/{userId}/{refresh_count}/{pageSize}")
    @ApiOperation("返回前端不同分类情况下的新闻 ,identity就是分类")
    public ResponseResult getRecNews(@PathVariable Long userId ,@PathVariable Integer refresh_count,  @PathVariable Integer pageSize) {
        return newsService.getRecNewsByHistory(userId , refresh_count, pageSize) ;
    }

    /**
     * TODO 根据id查询新闻信息 注意更新redis中的点赞数和收藏数
     */
    @GetMapping("/news/{id}")
    @ApiOperation("id查新闻")
    public ResponseResult selectNewsById(@PathVariable Long id) {
        return newsService.getNewsById(id) ;
    }

    /**
     * TODO 用户点赞，点赞数加1；取消赞；点赞数-1，根据参数确定，1代表点赞，0代表取消点赞
     */
    @GetMapping("/news/clickDigg/{id}/{isDigg}")
    @ApiOperation("用户点击点赞按钮事件")
    public ResponseResult clickDigg(@PathVariable Long id ,@PathVariable Boolean isDigg) {
        return newsService.clickDigg(id ,isDigg);
    }

    /**
     * TODO 用户点赞，点赞数加1；取消赞；点赞数-1，根据参数确定，1代表点赞，0代表取消点赞
     */
    @GetMapping("/news/clickDigg/{userId}/{newsId}/{isDigg}")
    @ApiOperation("用户点击点赞按钮事件")
    public ResponseResult userDigged(@PathVariable Long userId ,@PathVariable Long newsId ,@PathVariable Boolean isDigg) {
        return newsService.userDigged(userId, newsId ,isDigg);
    }

    /**
     * TODO 判断当前用户是否已经点赞过了
     */
    @GetMapping("/news/isDigg/{userId}/{newsId}")
    public ResponseResult isUserDigged(@PathVariable Long userId ,@PathVariable Long newsId) {
        return newsService.isUserDigged(userId , newsId);
    }

    /**
     * TODO 用户收藏功能
     */
    @GetMapping("/news/clickLike/{userId}/{newsId}/{isLike}")
    @ApiOperation("用户点击点赞按钮事件")
    public ResponseResult userLiked(@PathVariable Long userId ,@PathVariable Long newsId ,@PathVariable Boolean isLike) {
        return newsService.userLiked(userId, newsId ,isLike);
    }

    /**
     * TODO 判断当前用户是否已经收藏过了
     */
    @GetMapping("/news/isLike/{userId}/{newsId}")
    public ResponseResult isUserLiked(@PathVariable Long userId ,@PathVariable Long newsId) {
        return newsService.isUserLiked(userId , newsId);
    }

    /**
     * TODO 获取新闻作者信息
     */
    @GetMapping("/news/getAuthorById/{uid}")
    public ResponseResult getAuthorById(@PathVariable String uid) {
        return newsService.getAuthById(uid) ;
    }

    /**
     * TODO 根据作者id获取该作者发布的新闻
     */
    @GetMapping("/news/getNewsByAuthUid/{uid}")
    public ResponseResult getNewsByAuthUid(@PathVariable String uid) {
        return newsService.getNewsByAuthUid(uid) ;
    }

    /**
     * TODO 是否已关注该作者
     */
    @GetMapping("/news/isFollow/{userId}/{mediaUid}")
    public ResponseResult userIsFollow(@PathVariable Long userId , @PathVariable String mediaUid ) {
        return newsService.userIsFollow(userId , mediaUid) ;
    }

    /**
     * TODO 关注用户
     */
    @GetMapping("/news/followMedia/{userId}/{mediaUid}")
    public ResponseResult followMedia(@PathVariable Long userId , @PathVariable String mediaUid ) {
        return newsService.followMedia(userId , mediaUid) ;
    }

    /**
     * TODO 取关用户
     */
    @GetMapping("/news/cancelFollowMedia/{userId}/{mediaUid}")
    public ResponseResult cancelFollowMedia(@PathVariable Long userId , @PathVariable String mediaUid ) {
        return newsService.cancelFollowMedia(userId , mediaUid) ;
    }

    /**
     * TODO 根据关键字查询
     */
    @GetMapping("/news/getByKeyWord/{keyword}")
    public ResponseResult getNewsByTitle(@PathVariable String keyword) {
        return newsService.getNewsByTitle(keyword) ;
    }

    /**
     * TODO 插入查询记录
     */
    @GetMapping("/news/searchHistory/{userId}/{keyWord}")
    public ResponseResult addSearchHistory(@PathVariable Long userId , @PathVariable String keyWord) {
        return newsService.addSearchHistory(userId , keyWord) ;
    }

    /**
     * TODO 查询历史纪录
     */
    @GetMapping("/news/getSearchHistory/{userId}")
    public ResponseResult addSearchHistory(@PathVariable Long userId) {
        return newsService.getSearchHistory(userId) ;
    }

    /**
     * TODO 清空历史记录
     */
    @GetMapping("/news/deleteSearchHistory/{userId}")
    public ResponseResult deleteSearchHistory(@PathVariable Long userId) {
        return newsService.deleteSearchHistory(userId) ;
    }

    /**
     * TODO 获取用户的新闻媒体关注列表
     */
    @GetMapping("/news/getUserFollowAuthor/{userId}")
    public ResponseResult getUserFollowAuthor(@PathVariable Long userId) {
        return newsService.getUserFollowAuthor(userId) ;
    }

}

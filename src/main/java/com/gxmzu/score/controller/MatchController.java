package com.gxmzu.score.controller;

import com.gxmzu.score.domain.AjaxResult;
import com.gxmzu.score.domain.entity.Match;
import com.gxmzu.score.service.MatchService;
import com.gxmzu.score.utils.HttpStatus;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/10/10:35
 * @Description: 比赛接口
 */
@RestController
@RequestMapping("/match")
public class MatchController extends BaseController{

    @Resource
    private MatchService matchService;

    /**
     * 获取比赛列表
     *
     * @param match 比赛查询信息
     * @return 比赛列表
     */
    @GetMapping("/list")
    public AjaxResult getMatchServiceList(Match match) {
        startPage(match);
        List<Match> list = matchService.getMatchList(match);
        return getDataTable(list);
    }

    /**
     * 添加比赛信息
     *
     * @param match 比赛信息
     * @return
     */
    @PostMapping("/add")
    public AjaxResult addMatch(HttpServletRequest httpServletRequest, @RequestBody Match match){
        //判断传来的参数是否为空
        if(match.getMatchName().isEmpty()||match.getInfo().isEmpty()||match.getScoreRuleName().isEmpty()||match.getCreateBy().isEmpty()){
            return AjaxResult.error(HttpStatus.LACK_QUERY,"缺少请求参数");
        //判断传来的最高分是否合法
        }else if(match.getMaxScore() <= 0){
            return AjaxResult.error(HttpStatus.ERROR_QUERY,"请求参数有误");
        //参数无误后插入数据，并执行业务操作
        }else{
        return matchService.addMatch(httpServletRequest, match);
        }
    }

    /**
     * 更新比赛信息
     *
     * @param match 比赛信息
     * @return
     */
    @PutMapping("/update")
    public AjaxResult updateMatch(HttpServletRequest httpServletRequest, @RequestBody Match match){
        //判断传来的最高分是否合法
        if(match.getMaxScore() <= 0){
            return AjaxResult.error(HttpStatus.ERROR_QUERY,"请求参数有误");
        //参数无误后插入数据，并执行业务操作
        }else{
            return matchService.updateMatch(httpServletRequest,match);
        }
    }

    /**
     * 删除比赛信息
     *
     * @param matchId 删除的比赛id
     * @return
     */
    @DeleteMapping("/{matchId}")
    public AjaxResult deleteMatch(HttpServletRequest httpServletRequest, @PathVariable Integer matchId){
        //判断参数matchId是否合法
        if(matchId == null){
            return AjaxResult.error(HttpStatus.LACK_QUERY,"缺少请求参数");
        }else if(matchId <= 0){
            return AjaxResult.error(HttpStatus.ERROR_QUERY,"请求参数有误");
        //参数无误后执行业务操作
        }else{
            return matchService.deleteMatch(httpServletRequest, matchId);
        }
    }
}

package com.windtower.server.Repository;

import com.windtower.server.Entity.ServerFrame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @program: windtower
 * @description:
 * @author: wangtengke
 * @create: 2018-12-22
 **/
@Repository
public interface FrameServerRepository extends JpaRepository<ServerFrame, Long> {


}

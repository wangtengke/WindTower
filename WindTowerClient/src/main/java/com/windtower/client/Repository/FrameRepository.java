package com.windtower.client.Repository;

import com.windtower.client.Entity.Frame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @program: windtower
 * @description:
 * @author: wangtengke
 * @create: 2018-12-22
 **/
@Repository
public interface FrameRepository extends JpaRepository<Frame, Long> {


}

package com.itpro.community.service;

import com.itpro.community.dto.NotificationDTO;
import com.itpro.community.dto.PaginationDTO;
import com.itpro.community.pojo.User;

public interface NotificationService {
    PaginationDTO list(Integer id, Integer page, Integer size);

    Long unreadCount(Integer id);

    NotificationDTO read(Integer id, User user);
}

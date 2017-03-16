package com.sombra.project.service;

import com.sombra.project.dto.OrderDto;

public interface DocumentService {

	void createDocument(OrderDto orderDto, String fontUri);

}

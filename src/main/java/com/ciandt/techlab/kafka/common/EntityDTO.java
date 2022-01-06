package com.ciandt.techlab.kafka.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Builder
@Slf4j
@AllArgsConstructor
@RequiredArgsConstructor
public class EntityDTO {

	private Long offset;
	private Integer partition;
	private Object value;

}
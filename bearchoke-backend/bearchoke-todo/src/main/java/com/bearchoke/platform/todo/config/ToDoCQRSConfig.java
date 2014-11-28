/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bearchoke.platform.todo.config;

import com.bearchoke.platform.todo.ToDoItem;
import org.axonframework.contextsupport.spring.AnnotationDriven;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventstore.EventStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;

/**
 * Created by Bjorn Harvold
 * Date: 8/14/14
 * Time: 12:56 AM
 * Responsibility:
 */
@Configuration
@ComponentScan(basePackages = "com.bearchoke.platform.todo", excludeFilters = {
        @ComponentScan.Filter(value = Configuration.class, type = FilterType.ANNOTATION)
})
@AnnotationDriven
public class ToDoCQRSConfig {
    @Inject
    private EventStore eventStore;

    @Inject
    private EventBus eventBus;

    @Bean(name = "toDoItemRepository")
    public EventSourcingRepository<ToDoItem> toDoItemRepository() {
        EventSourcingRepository repository = new EventSourcingRepository<>(ToDoItem.class, eventStore);
        repository.setEventBus(eventBus);

        return repository;
    }
}
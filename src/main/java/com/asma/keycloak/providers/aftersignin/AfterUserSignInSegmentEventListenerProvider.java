package com.asma.keycloak.providers.aftersignin;

import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.models.KeycloakSession;
import com.segment.analytics.Analytics;
import com.segment.analytics.messages.TrackMessage;

public class AfterUserSignInSegmentEventListenerProvider implements EventListenerProvider {

    private final KeycloakSession session;

    public AfterUserSignInSegmentEventListenerProvider(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public void onEvent(Event event) {

        if (event.getType().equals(EventType.LOGIN)) {
            Analytics analytics = Analytics.builder(System.getenv("SEGMENT_KEY")).build();

            analytics.enqueue(
                    TrackMessage.builder("User Signed In")
                            .userId(event.getUserId()));
        }

    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {

    }

    @Override
    public void close() {

    }
}
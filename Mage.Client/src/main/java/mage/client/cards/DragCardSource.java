package mage.client.cards;

import mage.view.CardView;

import java.util.Collection;

/**
 * Created by StravantUser on 2016-09-22.
 */
public interface DragCardSource {
    Collection<CardView> dragCardList();
    void dragCardBegin();
    void dragCardEnd(DragCardTarget target);
}

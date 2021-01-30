package mage.client.cards;

import mage.view.CardView;

import java.util.Collection;

/**
 * @author StravantUser
 */
public interface DragCardSource {
    Collection<CardView> dragCardList();

    void dragCardBegin();

    void dragCardEnd(DragCardTarget target);
}

package mage.cards;

import mage.MageInt;

/**
 * @author JayDi85
 */
public interface ModalDoubleFacesCardHalf extends Card {

    @Override
    ModalDoubleFacesCardHalf copy();

    void setParentCard(ModalDoubleFacesCard card);

    ModalDoubleFacesCard getParentCard();

    void setPT(int power, int toughness);

    void setPT(MageInt power, MageInt toughness);
}

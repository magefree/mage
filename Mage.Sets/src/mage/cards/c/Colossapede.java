package mage.cards.c;

import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author Stravant
 */
public class Colossapede extends CardImpl {

    public Colossapede(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add("Insect");
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
    }

    public Colossapede(final Colossapede card) {
        super(card);
    }

    @Override
    public Colossapede copy() {
        return new Colossapede(this);
    }
}

package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Stravant
 */
public final class Colossapede extends CardImpl {

    public Colossapede(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
    }

    private Colossapede(final Colossapede card) {
        super(card);
    }

    @Override
    public Colossapede copy() {
        return new Colossapede(this);
    }
}

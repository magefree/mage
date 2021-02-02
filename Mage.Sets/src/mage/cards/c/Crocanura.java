package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.EvolveAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class Crocanura extends CardImpl {

    public Crocanura(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.CROCODILE);
        this.subtype.add(SubType.FROG);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        //Reach
        this.addAbility(ReachAbility.getInstance());

        // Evolve (Whenever a creature enters the battlefield under your control, if that creature
        // has greater power or toughness than this creature, put a +1/+1 counter on this creature.)
        this.addAbility(new EvolveAbility());

    }

    private Crocanura(final Crocanura card) {
        super(card);
    }

    @Override
    public Crocanura copy() {
        return new Crocanura(this);
    }
}

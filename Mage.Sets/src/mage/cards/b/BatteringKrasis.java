package mage.cards.b;

import mage.MageInt;
import mage.abilities.keyword.EvolveAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class BatteringKrasis extends CardImpl {

    public BatteringKrasis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.SHARK, SubType.BEAST);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Evolve (Whenever a creature enters the battlefield under your control, if that creature has greater power or toughness than this creature, put a +1/+1 counter on this creature.)
        this.addAbility(new EvolveAbility());
    }

    private BatteringKrasis(final BatteringKrasis card) {
        super(card);
    }

    @Override
    public BatteringKrasis copy() {
        return new BatteringKrasis(this);
    }

}

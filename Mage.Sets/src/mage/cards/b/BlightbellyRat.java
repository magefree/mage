package mage.cards.b;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.constants.SubType;
import mage.abilities.keyword.ToxicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author TheElk801
 */
public final class BlightbellyRat extends CardImpl {

    public BlightbellyRat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.RAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Toxic 1
        this.addAbility(new ToxicAbility(1));

        // When Blightbelly Rat dies, proliferate.
        this.addAbility(new DiesSourceTriggeredAbility(new ProliferateEffect()));
    }

    private BlightbellyRat(final BlightbellyRat card) {
        super(card);
    }

    @Override
    public BlightbellyRat copy() {
        return new BlightbellyRat(this);
    }
}

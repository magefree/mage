package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByOneEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RampagingCeratops extends CardImpl {

    public RampagingCeratops(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Rampaging Ceratops can't be blocked except by three or more creatures.
        this.addAbility(new SimpleStaticAbility(new CantBeBlockedByOneEffect(3)));
    }

    private RampagingCeratops(final RampagingCeratops card) {
        super(card);
    }

    @Override
    public RampagingCeratops copy() {
        return new RampagingCeratops(this);
    }
}

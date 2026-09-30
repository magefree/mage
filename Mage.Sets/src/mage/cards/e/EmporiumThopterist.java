package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ConjureCardEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class EmporiumThopterist extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.THOPTER, "Thopters you control");

    public EmporiumThopterist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Thopters you control get +2/+0.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(2, 0, Duration.WhileOnBattlefield, filter)));

        // At the beginning of your upkeep, conjure a card named Ornithopter into your hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
            new ConjureCardEffect("Ornithopter")
        ));
    }

    private EmporiumThopterist(final EmporiumThopterist card) {
        super(card);
    }

    @Override
    public EmporiumThopterist copy() {
        return new EmporiumThopterist(this);
    }
}

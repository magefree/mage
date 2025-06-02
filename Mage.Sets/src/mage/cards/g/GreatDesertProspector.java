package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.PowerstoneToken;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class GreatDesertProspector extends CardImpl {

    private static final PermanentsOnBattlefieldCount count = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_OTHER_CONTROLLED_CREATURE);

    public GreatDesertProspector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Great Desert Prospector enters the battlefield, create a tapped Powerstone token for each other creature you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new PowerstoneToken(), count, true, false)));
    }

    private GreatDesertProspector(final GreatDesertProspector card) {
        super(card);
    }

    @Override
    public GreatDesertProspector copy() {
        return new GreatDesertProspector(this);
    }
}

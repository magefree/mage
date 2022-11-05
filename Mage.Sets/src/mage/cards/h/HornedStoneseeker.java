package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.constants.SubType;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.game.permanent.token.PowerstoneToken;

/**
 *
 * @author weirddan455
 */
public final class HornedStoneseeker extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.POWERSTONE, "Powerstone");

    public HornedStoneseeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.LIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility());

        // When Horned Stoneseeker enters the battlefield, create a tapped Powerstone token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new PowerstoneToken(), 1, true)));

        // When Horned Stoneseeker leaves the battlefield, sacrifice a Powerstone.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new SacrificeControllerEffect(filter, 1, null), false));
    }

    private HornedStoneseeker(final HornedStoneseeker card) {
        super(card);
    }

    @Override
    public HornedStoneseeker copy() {
        return new HornedStoneseeker(this);
    }
}

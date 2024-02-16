package mage.cards.s;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.CitizenGreenWhiteToken;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StimulusPackage extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.TREASURE, "a Treasure");

    public StimulusPackage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}{G}");

        // When Stimulus Package enters the battlefield, create two Treasure tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new TreasureToken(), 2)));

        // Sacrifice a Treasure: Create a 1/1 green and white Citizen creature token.
        this.addAbility(new SimpleActivatedAbility(
                new CreateTokenEffect(new CitizenGreenWhiteToken()), new SacrificeTargetCost(filter)
        ));
    }

    private StimulusPackage(final StimulusPackage card) {
        super(card);
    }

    @Override
    public StimulusPackage copy() {
        return new StimulusPackage(this);
    }
}

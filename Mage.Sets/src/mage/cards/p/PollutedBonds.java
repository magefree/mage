package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOpponentTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class PollutedBonds extends CardImpl {

    public PollutedBonds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{B}");

        // Whenever a land enters the battlefield under an opponent's control, that player loses 2 life and you gain 2 life.
        Ability ability = new EntersBattlefieldOpponentTriggeredAbility(Zone.BATTLEFIELD,
                new LoseLifeTargetEffect(2), StaticFilters.FILTER_LAND_A, false, SetTargetPointer.PLAYER);
        ability.addEffect(new GainLifeEffect(2).concatBy("and"));
        this.addAbility(ability);

    }

    private PollutedBonds(final PollutedBonds card) {
        super(card);
    }

    @Override
    public PollutedBonds copy() {
        return new PollutedBonds(this);
    }
}

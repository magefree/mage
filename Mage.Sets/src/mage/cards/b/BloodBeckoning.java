package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BloodBeckoning extends CardImpl {

    public BloodBeckoning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // Kicker {3}
        this.addAbility(new KickerAbility("{3}"));

        // Return target creature card from your graveyard to your hand. If this spell was kicked, instead return two target creature cards from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect()
                .setText("return target creature card from your graveyard to your hand. If this spell was kicked, " +
                        "instead return two target creature cards from your graveyard to your hand"));
        this.getSpellAbility().setTargetAdjuster(BloodBeckoningAdjuster.instance);
    }

    private BloodBeckoning(final BloodBeckoning card) {
        super(card);
    }

    @Override
    public BloodBeckoning copy() {
        return new BloodBeckoning(this);
    }
}

enum BloodBeckoningAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        if (KickedCondition.ONCE.apply(game, ability)) {
            ability.addTarget(new TargetCardInYourGraveyard(2, StaticFilters.FILTER_CARD_CREATURE));
        } else {
            ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE));
        }
    }
}

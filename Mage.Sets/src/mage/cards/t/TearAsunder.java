package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.target.common.TargetNonlandPermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TearAsunder extends CardImpl {

    public TearAsunder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Kicker {1}{B}
        this.addAbility(new KickerAbility("{1}{B}"));

        // Exile target artifact or enchantment. If this spell was kicked, exile target nonland permanent instead.
        this.getSpellAbility().addEffect(new ExileTargetEffect().setText("Exile target artifact or enchantment. If this spell was kicked, exile target nonland permanent instead."));
        this.getSpellAbility().setTargetAdjuster(TearAsunderAdjuster.instance);
    }

    private TearAsunder(final TearAsunder card) {
        super(card);
    }

    @Override
    public TearAsunder copy() {
        return new TearAsunder(this);
    }
}

enum TearAsunderAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        if (KickedCondition.ONCE.apply(game, ability)) {
            ability.addTarget(new TargetNonlandPermanent());
        } else {
            ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        }
    }
}
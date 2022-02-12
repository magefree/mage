package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class ToxicStench extends CardImpl {

    public ToxicStench(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Target nonblack creature gets -1/-1 until end of turn.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new ToxicStenchEffect(),
                new InvertCondition(new CardsInControllerGraveyardCondition(7)),
                "Target nonblack creature gets -1/-1 until end of turn."));

        // Threshold - If seven or more cards are in your graveyard, instead destroy that creature. It can't be regenerated.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DestroyTargetEffect(true),
                new CardsInControllerGraveyardCondition(7),
                "<br/><br/><i>Threshold</i> &mdash; If seven or more cards are in your graveyard, instead destroy that creature. It can't be regenerated."));

        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_PERMANENT_CREATURE_NON_BLACK));
    }

    private ToxicStench(final ToxicStench card) {
        super(card);
    }

    @Override
    public ToxicStench copy() {
        return new ToxicStench(this);
    }
}

class ToxicStenchEffect extends OneShotEffect {

    ToxicStenchEffect() {
        super(Outcome.UnboostCreature);
    }

    ToxicStenchEffect(final ToxicStenchEffect effect) {
        super(effect);
    }

    @Override
    public ToxicStenchEffect copy() {
        return new ToxicStenchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ContinuousEffect effect = new BoostTargetEffect(-1, -1, Duration.EndOfTurn);
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            effect.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }
}

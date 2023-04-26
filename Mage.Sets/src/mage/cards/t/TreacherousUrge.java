package mage.cards.t;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J
 */
public final class TreacherousUrge extends CardImpl {

    public TreacherousUrge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{B}");

        // Target opponent reveals their hand. You may put a creature card from it onto the battlefield under your control. That creature gains haste. Sacrifice it at the beginning of the next end step.
        this.getSpellAbility().addEffect(new TreacherousUrgeEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private TreacherousUrge(final TreacherousUrge card) {
        super(card);
    }

    @Override
    public TreacherousUrge copy() {
        return new TreacherousUrge(this);
    }
}

class TreacherousUrgeEffect extends OneShotEffect {

    public TreacherousUrgeEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target opponent reveals their hand. You may put a creature card from it onto the battlefield under your control. That creature gains haste. Sacrifice it at the beginning of the next end step";
    }

    public TreacherousUrgeEffect(final TreacherousUrgeEffect effect) {
        super(effect);
    }

    @Override
    public TreacherousUrgeEffect copy() {
        return new TreacherousUrgeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(targetPointer.getFirst(game, source));
        MageObject sourceObject = game.getObject(source);
        if (opponent != null && sourceObject != null) {
            opponent.revealCards(sourceObject.getName(), opponent.getHand(), game);
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                int cardsHand = opponent.getHand().count(StaticFilters.FILTER_CARD_CREATURE, game);
                Card card = null;
                if (cardsHand > 0) {
                    TargetCard target = new TargetCard(Zone.HAND, StaticFilters.FILTER_CARD_CREATURE);
                    if (controller.choose(Outcome.Benefit, opponent.getHand(), target, source, game)) {
                        card = opponent.getHand().get(target.getFirstTarget(), game);
                        if (card != null) {
                            if (controller.moveCards(card, Zone.BATTLEFIELD, source, game)) {
                                Permanent permanent = game.getPermanent(card.getId());
                                if (permanent != null) {
                                    ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.Custom);
                                    effect.setTargetPointer(new FixedTarget(permanent, game));
                                    game.addEffect(effect, source);
                                    SacrificeTargetEffect sacrificeEffect = new SacrificeTargetEffect("sacrifice " + card.getName(), source.getControllerId());
                                    sacrificeEffect.setTargetPointer(new FixedTarget(permanent, game));
                                    DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(sacrificeEffect);
                                    game.addDelayedTriggeredAbility(delayedAbility, source);
                                    return true;
                                }
                            }
                        }
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
}

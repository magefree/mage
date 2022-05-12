package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.SpliceOntoArcaneAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class ThroughTheBreach extends CardImpl {

    public ThroughTheBreach(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{R}");
        this.subtype.add(SubType.ARCANE);

        // You may put a creature card from your hand onto the battlefield. That creature gains haste. Sacrifice that creature at the beginning of the next end step.
        this.getSpellAbility().addEffect(new ThroughTheBreachEffect());
        // Splice onto Arcane {2}{R}{R}
        this.addAbility(new SpliceOntoArcaneAbility(new ManaCostsImpl("{2}{R}{R}")));
    }

    private ThroughTheBreach(final ThroughTheBreach card) {
        super(card);
    }

    @Override
    public ThroughTheBreach copy() {
        return new ThroughTheBreach(this);
    }
}

class ThroughTheBreachEffect extends OneShotEffect {

    private static final String choiceText = "Put a creature card from your hand onto the battlefield?";

    public ThroughTheBreachEffect() {
        super(Outcome.Benefit);
        this.staticText = "You may put a creature card from your hand onto the battlefield. That creature gains haste. Sacrifice that creature at the beginning of the next end step";
    }

    public ThroughTheBreachEffect(final ThroughTheBreachEffect effect) {
        super(effect);
    }

    @Override
    public ThroughTheBreachEffect copy() {
        return new ThroughTheBreachEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.chooseUse(Outcome.PutCreatureInPlay, choiceText, source, game)) {
                TargetCardInHand target = new TargetCardInHand(StaticFilters.FILTER_CARD_CREATURE);
                if (controller.choose(Outcome.PutCreatureInPlay, target, source, game)) {
                    Card card = game.getCard(target.getFirstTarget());
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
            return true;
        }
        return false;
    }
}

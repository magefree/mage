package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

import java.util.Set;
import java.util.UUID;

/**
 * @author LePwnerer
 */
public final class WakeToSlaughter extends CardImpl {

    public WakeToSlaughter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{R}");

        // Choose up to two target creature cards in your graveyard. An opponent chooses one of them. Return that card to your hand. Return the other to the battlefield under your control. It gains haste. Exile it at the beginning of the next end step.
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(0, 2, StaticFilters.FILTER_CARD_CREATURE));
        this.getSpellAbility().addEffect(new WakeToSlaughterEffect());

        // Flashback {4}{B}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{4}{B}{R}")));

    }

    private WakeToSlaughter(final WakeToSlaughter card) {
        super(card);
    }

    @Override
    public WakeToSlaughter copy() {
        return new WakeToSlaughter(this);
    }
}

class WakeToSlaughterEffect extends OneShotEffect {

    public WakeToSlaughterEffect() {
        super(Outcome.Benefit);
        this.staticText = "Choose up to two target creature cards in your graveyard. " +
                "An opponent chooses one of them. " +
                "Return that card to your hand. " +
                "Return the other to the battlefield under your control. " +
                "It gains haste. " +
                "Exile it at the beginning of the next end step.";
    }

    public WakeToSlaughterEffect(final mage.cards.w.WakeToSlaughterEffect effect) {
        super(effect);
    }

    @Override
    public mage.cards.w.WakeToSlaughterEffect copy() {
        return new mage.cards.w.WakeToSlaughterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Cards pickedCards = new CardsImpl(getTargetPointer().getTargets(game, source));
        if (player != null && !pickedCards.isEmpty()) {
            Card cardToHand;
            if (pickedCards.size() == 1) {
                cardToHand = pickedCards.getRandom(game);
            } else {
                Player opponent;
                Set<UUID> opponents = game.getOpponents(player.getId());
                if (opponents.size() == 1) {
                    opponent = game.getPlayer(opponents.iterator().next());
                } else {
                    Target targetOpponent = new TargetOpponent(true);
                    player.chooseTarget(Outcome.Detriment, targetOpponent, source, game);
                    opponent = game.getPlayer(targetOpponent.getFirstTarget());
                }

                TargetCard target = new TargetCard(1, Zone.GRAVEYARD, new FilterCard());
                target.withChooseHint("Card to go to opponent's hand (other goes to battlefield)");
                opponent.chooseTarget(outcome, pickedCards, target, source, game);
                cardToHand = game.getCard(target.getFirstTarget());
            }
            for (Card card : pickedCards.getCards(game)) {
                if (card == cardToHand) {
                    player.moveCards(cardToHand, Zone.HAND, source, game);
                } else {
                    player.moveCards(card, Zone.BATTLEFIELD, source, game);

                    FixedTarget fixedTarget = new FixedTarget(card, game);
                    ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfGame);
                    effect.setTargetPointer(fixedTarget);
                    game.addEffect(effect, source);

                    ExileTargetEffect exileEffect = new ExileTargetEffect(null, null, Zone.BATTLEFIELD);
                    exileEffect.setTargetPointer(fixedTarget);
                    DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(exileEffect);
                    game.addDelayedTriggeredAbility(delayedAbility, source);
                }
            }
            pickedCards.clear();
            return true;
        }

        return false;
    }
}

package mage.cards.a;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.DistributeCountersEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Card;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.abilities.keyword.CompleatedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.command.emblems.AjaniSleeperAgentEmblem;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanentAmount;

/**
 *
 * @author weirddan455
 */
public final class AjaniSleeperAgent extends CardImpl {

    public AjaniSleeperAgent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{G}{G/W/P}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.AJANI);
        this.setStartingLoyalty(4);

        // Compleated
        this.addAbility(CompleatedAbility.getInstance());

        // +1: Reveal the top card of your library. If it's a creature or planeswalker card, put it into your hand. Otherwise, you may put it on the bottom of your library.
        this.addAbility(new LoyaltyAbility(new AjaniSleeperAgentEffect(), 1));

        // −3: Distribute three +1/+1 counters among up to three target creatures. They gain vigilance until end of turn.
        Ability ability = new LoyaltyAbility(new DistributeCountersEffect(CounterType.P1P1, 3, false, "up to three target creatures"), -3);
        ability.addEffect(new GainAbilityTargetEffect(VigilanceAbility.getInstance()).setText("They gain vigilance until end of turn"));
        Target target = new TargetCreaturePermanentAmount(3);
        target.setMinNumberOfTargets(0);
        target.setMaxNumberOfTargets(3);
        ability.addTarget(target);
        this.addAbility(ability);

        // −6: You get an emblem with "Whenever you cast a creature or planeswalker spell, target opponent gets two poison counters."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new AjaniSleeperAgentEmblem()), -6));
    }

    private AjaniSleeperAgent(final AjaniSleeperAgent card) {
        super(card);
    }

    @Override
    public AjaniSleeperAgent copy() {
        return new AjaniSleeperAgent(this);
    }
}

class AjaniSleeperAgentEffect extends OneShotEffect {

    public AjaniSleeperAgentEffect() {
        super(Outcome.Benefit);
        this.staticText = "Reveal the top card of your library. If it's a creature or planeswalker card, put it into your hand. Otherwise, you may put it on the bottom of your library.";
    }

    private AjaniSleeperAgentEffect(final AjaniSleeperAgentEffect effect) {
        super(effect);
    }

    @Override
    public AjaniSleeperAgentEffect copy() {
        return new AjaniSleeperAgentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card card = controller.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        controller.revealCards(source, new CardsImpl(card), game);
        if (card.isCreature(game) || card.isPlaneswalker(game)) {
            controller.moveCards(card, Zone.HAND, source, game);
        } else if (controller.chooseUse(Outcome.Neutral, "Put " + card.getName() + " on the bottom of your library?", source, game)) {
            controller.putCardsOnBottomOfLibrary(card, game, source, true);
        }
        return true;
    }
}

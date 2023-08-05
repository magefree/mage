package mage.cards.j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainSuspendEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterNonlandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 * @author LevelX2
 */
public final class JhoiraOfTheGhitu extends CardImpl {

    public JhoiraOfTheGhitu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {2}, Exile a nonland card from your hand: Put four time counters on the exiled card. If it doesn't have suspend, it gains suspend.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new JhoiraOfTheGhituSuspendEffect(), new GenericManaCost(2));
        ability.addCost(new ExileFromHandCost(new TargetCardInHand(new FilterNonlandCard("a nonland card from your hand"))));
        this.addAbility(ability);

    }

    private JhoiraOfTheGhitu(final JhoiraOfTheGhitu card) {
        super(card);
    }

    @Override
    public JhoiraOfTheGhitu copy() {
        return new JhoiraOfTheGhitu(this);
    }
}

class JhoiraOfTheGhituSuspendEffect extends OneShotEffect {

    public JhoiraOfTheGhituSuspendEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Put four time counters on the exiled card. If it doesn't have suspend, it gains suspend. <i>(At the beginning of your upkeep, remove a time counter from that card. When the last is removed, cast it without paying its mana cost. If it's a creature, it has haste.)</i>";
    }

    public JhoiraOfTheGhituSuspendEffect(final JhoiraOfTheGhituSuspendEffect effect) {
        super(effect);
    }

    @Override
    public JhoiraOfTheGhituSuspendEffect copy() {
        return new JhoiraOfTheGhituSuspendEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        List<Card> cards = new ArrayList<>();
        for (Cost cost : source.getCosts()) {
            if (cost instanceof ExileFromHandCost) {
                cards = ((ExileFromHandCost) cost).getCards();
            }
        }
        if (cards != null && !cards.isEmpty()) {
            // always one card to exile
            Card card = game.getCard(cards.get(0).getId());
            if (card == null) {
                return false;
            }
            card = card.getMainCard();

            boolean hasSuspend = card.getAbilities(game).containsClass(SuspendAbility.class);

            UUID exileId = SuspendAbility.getSuspendExileId(controller.getId(), game);
            if (controller.moveCardToExileWithInfo(card, exileId, "Suspended cards of " + controller.getName(), source, game, Zone.HAND, true)) {
                card.addCounters(CounterType.TIME.createInstance(4), source.getControllerId(), source, game);
                if (!hasSuspend) {
                    game.addEffect(new GainSuspendEffect(new MageObjectReference(card, game)), source);
                }
                game.informPlayers(controller.getLogName() + " suspends 4 - " + card.getName());
                return true;
            }
        }
        return false;
    }
}

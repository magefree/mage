package mage.cards.p;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.CollectEvidenceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.PutCards;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetDiscard;
import mage.target.common.TargetSacrifice;

/**
 *
 * @author DominionSpy
 */
public final class PolygraphOrb extends CardImpl {

    public PolygraphOrb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}{B}");

        // When Polygraph Orb enters the battlefield, look at the top four cards of your library.
        // Put two of them into your hand and the rest into your graveyard. You lose 2 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new LookLibraryAndPickControllerEffect(
                        4, 2, PutCards.HAND, PutCards.GRAVEYARD));
        ability.addEffect(new LoseLifeSourceControllerEffect(2));
        this.addAbility(ability);

        // {2}, {T}, Collect evidence 3: Each opponent loses 3 life unless they discard a card or sacrifice a creature.
        ability = new SimpleActivatedAbility(new PolygraphOrbEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new CollectEvidenceCost(3));
        this.addAbility(ability);
    }

    private PolygraphOrb(final PolygraphOrb card) {
        super(card);
    }

    @Override
    public PolygraphOrb copy() {
        return new PolygraphOrb(this);
    }
}

class PolygraphOrbEffect extends OneShotEffect {

    PolygraphOrbEffect() {
        super(Outcome.Benefit);
        staticText = "Each opponent loses 3 life unless they discard a card or sacrifice a creature";
    }

    private PolygraphOrbEffect(final PolygraphOrbEffect effect) {
        super(effect);
    }

    @Override
    public PolygraphOrbEffect copy() {
        return new PolygraphOrbEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        Map<UUID, Card> chosenCards = new HashMap<>();
        for (UUID opponentId : game.getOpponents(controller.getId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null) {
                continue;
            }

            TargetDiscard targetDiscard = new TargetDiscard(0, 1, StaticFilters.FILTER_CARD_A, opponentId);
            targetDiscard.withChooseHint("otherwise you have to sacrifice a creature or lose 3 life");
            if (opponent.choose(Outcome.PreventDamage, targetDiscard, source, game)) {
                chosenCards.put(opponentId, game.getCard(targetDiscard.getFirstTarget()));
                continue;
            }

            TargetSacrifice targetSacrifice = new TargetSacrifice(0, 1, StaticFilters.FILTER_CONTROLLED_CREATURE);
            targetSacrifice.withChooseHint("otherwise you lose 3 life");
            if (opponent.choose(Outcome.PreventDamage, targetSacrifice, source, game)) {
                chosenCards.put(opponentId, game.getCard(targetSacrifice.getFirstTarget()));
                continue;
            }

            chosenCards.put(opponentId, null);
        }
        for (Map.Entry<UUID, Card> entry : chosenCards.entrySet()) {
            Player opponent = game.getPlayer(entry.getKey());
            if (opponent == null) {
                continue;
            }
            if (entry.getValue() != null) {
                Card card = entry.getValue();
                Zone zone = game.getState().getZone(card.getId());
                if (Zone.HAND.match(zone)) {
                    opponent.discard(card, false, source, game);
                    continue;
                } else if (Zone.BATTLEFIELD.match(zone)) {
                    Permanent permanent = game.getPermanent(card.getId());
                    if (permanent != null) {
                        permanent.sacrifice(source, game);
                        continue;
                    }
                }
            }
            opponent.loseLife(3, game, source, false);
        }
        return true;
    }
}

package mage.cards.p;

import java.util.HashSet;
import java.util.Set;
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
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.PutCards;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
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

    private static final String DISCARD_CHOICE = "Discard a card";
    private static final String SACRIFICE_CHOICE = "Sacrifice a creature";
    private static final String LIFE_LOSS_CHOICE = "Lose 3 life";

    PolygraphOrbEffect() {
        super(Outcome.Detriment);
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

        for (UUID opponentId : game.getOpponents(controller.getId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null) {
                continue;
            }

            Set<String> choiceSet = new HashSet<>();
            if (opponent.getHand().size() > 0) {
                choiceSet.add(DISCARD_CHOICE);
            }
            if (game.getBattlefield().count(StaticFilters.FILTER_CONTROLLED_CREATURE, opponentId, source, game) > 0) {
                choiceSet.add(SACRIFICE_CHOICE);
            }
            choiceSet.add(LIFE_LOSS_CHOICE);

            String chosen;
            if (choiceSet.size() > 1) {
                Choice choice = new ChoiceImpl(true);
                choice.setChoices(choiceSet);
                opponent.choose(outcome, choice, game);
                chosen = choice.getChoice();
                if (chosen == null) {
                    chosen = LIFE_LOSS_CHOICE;
                }
            } else {
                chosen = LIFE_LOSS_CHOICE;
            }

            switch (chosen) {
                case DISCARD_CHOICE:
                    opponent.discard(1, false, false, source, game);
                    break;
                case SACRIFICE_CHOICE:
                    TargetSacrifice target = new TargetSacrifice(StaticFilters.FILTER_CONTROLLED_CREATURE);
                    Permanent permanent = null;
                    while (permanent == null) {
                        opponent.choose(Outcome.Sacrifice, target, source, game);
                        permanent = game.getPermanent(target.getFirstTarget());
                    }
                    permanent.sacrifice(source, game);
                    break;
                case LIFE_LOSS_CHOICE:
                    opponent.loseLife(3, game, source, false);
                    break;
            }
        }
        return true;
    }
}

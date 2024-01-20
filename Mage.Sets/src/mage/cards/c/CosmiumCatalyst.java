package mage.cards.c;

import java.util.UUID;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInExile;
import mage.util.CardUtil;

/**
 * Cosmium Catalyst
 * Artifact
 * {1}{R}, {T}: Choose an exiled card used to craft Cosmium Catalyst at random. You may cast that card without paying its mana cost.
 *
 * @author DominionSpy
 */
public class CosmiumCatalyst extends CardImpl {

    public CosmiumCatalyst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "");

        this.nightCard = true;
        this.color.setRed(true);

        // {1}{R}, {T}: Choose an exiled card used to craft Cosmium Catalyst at random. You may cast that card without paying its mana cost.
        Ability ability = new SimpleActivatedAbility(new CosmiumCatalystEffect(), new ManaCostsImpl<>("{1}{R}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private CosmiumCatalyst(CosmiumCatalyst card) {
        super(card);
    }

    @Override
    public CosmiumCatalyst copy() {
        return new CosmiumCatalyst(this);
    }
}

class CosmiumCatalystEffect extends OneShotEffect {

    CosmiumCatalystEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "Choose an exiled card used to craft {this} at random." +
                " You may cast that card without paying its mana cost.";
    }

    private CosmiumCatalystEffect(CosmiumCatalystEffect effect) {
        super(effect);
    }

    @Override
    public CosmiumCatalystEffect copy() {
        return new CosmiumCatalystEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (sourceObject == null) {
            return false;
        }

        Target target = new TargetCardInExile(StaticFilters.FILTER_CARD,
                CardUtil.getExileZoneId(game, source.getSourceId(),
                        game.getState().getZoneChangeCounter(source.getSourceId()) - 2
                ));
        target.withNotTarget(true);
        target.setRandom(true);
        if (!target.canChoose(controller.getId(), source, game)) {
            return true;
        }
        controller.chooseTarget(outcome, target, source, game);
        Card chosenCard = game.getCard(target.getFirstTarget());
        if (chosenCard != null) {
            CardUtil.castSpellWithAttributesForFree(controller, source, game, chosenCard);
        }
        return true;
    }
}

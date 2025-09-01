package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.condition.common.ForetoldCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class HauntingVoyage extends CardImpl {

    public HauntingVoyage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}{B}");

        // Choose a creature type. Return up to two creature cards of the chosen type from your graveyard to the battlefield.
        // If this spell was foretold, return all creature cards of the chosen type from your graveyard to the battlefield instead.
        this.getSpellAbility().addEffect(new ChooseCreatureTypeEffect(Outcome.PutCreatureInPlay));
        this.getSpellAbility().addEffect(new HauntingVoyageEffect());

        // Foretell {5}{B}{B}
        this.addAbility(new ForetellAbility(this, "{5}{B}{B}"));
    }

    private HauntingVoyage(final HauntingVoyage card) {
        super(card);
    }

    @Override
    public HauntingVoyage copy() {
        return new HauntingVoyage(this);
    }
}

class HauntingVoyageEffect extends OneShotEffect {

    HauntingVoyageEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Return up to two creature cards of that type from your graveyard to the battlefield. "
                + "If this spell was foretold, return all creature cards of that type from your graveyard to the battlefield instead";
    }

    private HauntingVoyageEffect(final HauntingVoyageEffect effect) {
        super(effect);
    }

    @Override
    public HauntingVoyageEffect copy() {
        return new HauntingVoyageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        SubType chosenSubType = ChooseCreatureTypeEffect.getChosenCreatureType(source.getSourceId(), game);
        if (controller == null || chosenSubType == null) {
            return false;
        }
        FilterCard filter = new FilterCard(chosenSubType);
        if (ForetoldCondition.instance.apply(game, source)) {
            return controller.moveCards(controller.getGraveyard().getCards(filter, game), Zone.BATTLEFIELD, source, game);
        }
        TargetCard target = new TargetCardInYourGraveyard(0, 2, filter, true);
        controller.chooseTarget(outcome, target, source, game);
        return controller.moveCards(new CardsImpl(target.getTargets()), Zone.BATTLEFIELD, source, game);
    }
}

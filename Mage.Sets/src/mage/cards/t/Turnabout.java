package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author Plopman
 */
public final class Turnabout extends CardImpl {

    public Turnabout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}{U}");

        // Choose artifact, creature, or land. Tap all untapped permanents of the chosen type target player controls, or untap all tapped permanents of that type that player controls.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new TurnaboutEffect());
    }

    private Turnabout(final Turnabout card) {
        super(card);
    }

    @Override
    public Turnabout copy() {
        return new Turnabout(this);
    }
}

class TurnaboutEffect extends OneShotEffect {

    private static final Set<String> choice = new HashSet<>();

    static {
        choice.add(CardType.ARTIFACT.toString());
        choice.add(CardType.CREATURE.toString());
        choice.add(CardType.LAND.toString());
    }

    public TurnaboutEffect() {
        super(Outcome.Benefit);
        staticText = "Choose artifact, creature, or land. Tap all untapped permanents of the chosen type target player controls, or untap all tapped permanents of that type that player controls";
    }

    public TurnaboutEffect(final TurnaboutEffect effect) {
        super(effect);
    }

    @Override
    public TurnaboutEffect copy() {
        return new TurnaboutEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || game.getPlayer(source.getFirstTarget()) == null) {
            return true;
        }
        Choice choiceImpl = new ChoiceImpl(true);
        choiceImpl.setMessage("Choose card type to tap or untap");
        choiceImpl.setChoices(choice);
        if (!controller.choose(Outcome.Neutral, choiceImpl, game)) {
            return false;
        }
        FilterPermanent filter;
        switch (choiceImpl.getChoice()) {
            case "Artifact":
                filter = StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT;
                break;
            case "Land":
                filter = StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND;
                break;
            case "Creature":
                filter = StaticFilters.FILTER_CONTROLLED_CREATURE;
                break;
            default:
                throw new IllegalArgumentException("Choice is required");

        }
        boolean tap = controller.chooseUse(
                Outcome.Neutral, "Tap or untap?", null,
                "Tap", "Untap", source, game
        );
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                filter, source.getFirstTarget(), source.getSourceId(), game
        )) {
            if (tap) {
                permanent.tap(source, game);
            } else {
                permanent.untap(game);
            }
        }
        return true;
    }
}

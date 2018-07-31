
package mage.cards.t;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author Plopman
 */
public final class Turnabout extends CardImpl {

    public Turnabout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}{U}");

        // Choose artifact, creature, or land. Tap all untapped permanents of the chosen type target player controls, or untap all tapped permanents of that type that player controls.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new TurnaboutEffect());

    }

    public Turnabout(final Turnabout card) {
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

    private static final Set<String> choice2 = new HashSet<>();

    static {
        choice2.add("Untap");
        choice2.add("Tap");
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
        UUID target = source.getFirstTarget();
        if (controller != null && target != null) {
            Choice choiceImpl = new ChoiceImpl();
            choiceImpl.setMessage("Choose card type to tap or untap");
            choiceImpl.setChoices(choice);
            if (!controller.choose(Outcome.Neutral, choiceImpl, game)) {
                return false;
            }
            CardType type;
            String choosenType = choiceImpl.getChoice();

            if (choosenType.equals(CardType.ARTIFACT.toString())) {
                type = CardType.ARTIFACT;
            } else if (choosenType.equals(CardType.LAND.toString())) {
                type = CardType.LAND;
            } else {
                type = CardType.CREATURE;
            }

            choiceImpl = new ChoiceImpl();
            choiceImpl.setMessage("Choose to tap or untap");
            choiceImpl.setChoices(choice2);
            if (!controller.choose(Outcome.Neutral, choiceImpl, game)) {
                return false;
            }

            FilterPermanent filter = new FilterPermanent();
            filter.add(new CardTypePredicate(type));

            if (choiceImpl.getChoice().equals("Untap")) {
                filter.add(new TappedPredicate());
                for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
                    if (permanent.isControlledBy(target)) {
                        permanent.untap(game);
                    }
                }
            } else {
                filter.add(Predicates.not(new TappedPredicate()));
                for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
                    if (permanent.isControlledBy(target)) {
                        permanent.tap(game);
                    }
                }
            }

        }

        return true;
    }
}

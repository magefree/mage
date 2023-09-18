
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColorOrArtifact;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/**
 * @author Loki
 */
public final class ApostlesBlessing extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("artifact or creature you control");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()));
    }

    public ApostlesBlessing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W/P}");

        // ({W/P} can be paid with either {W} or 2 life.)
        // Target artifact or creature you control gains protection from artifacts or from the color of your choice until end of turn.
        this.getSpellAbility().addEffect(new ApostlesBlessingEffect());
        this.getSpellAbility().addTarget(new TargetControlledPermanent(filter));
    }

    private ApostlesBlessing(final ApostlesBlessing card) {
        super(card);
    }

    @Override
    public ApostlesBlessing copy() {
        return new ApostlesBlessing(this);
    }

}

class ApostlesBlessingEffect extends OneShotEffect {

    public ApostlesBlessingEffect() {
        super(Outcome.AddAbility);
        this.staticText = "Target artifact or creature you control gains protection from artifacts or from the color of your choice until end of turn";
    }

    private ApostlesBlessingEffect(final ApostlesBlessingEffect effect) {
        super(effect);
    }

    @Override
    public ApostlesBlessingEffect copy() {
        return new ApostlesBlessingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            ChoiceColorOrArtifact choice = new ChoiceColorOrArtifact();
            if (controller.choose(outcome, choice, game)) {
                FilterCard protectionFilter = new FilterCard();
                if (choice.isArtifactSelected()) {
                    protectionFilter.add(CardType.ARTIFACT.getPredicate());
                } else {
                    protectionFilter.add(new ColorPredicate(choice.getColor()));
                }
                protectionFilter.setMessage(choice.getChoice());
                ProtectionAbility protectionAbility = new ProtectionAbility(protectionFilter);
                ContinuousEffect effect = new GainAbilityTargetEffect(protectionAbility, Duration.EndOfTurn);
                effect.setTargetPointer(getTargetPointer());
                game.addEffect(effect, source);
                return true;
            }
        }
        return false;
    }
}

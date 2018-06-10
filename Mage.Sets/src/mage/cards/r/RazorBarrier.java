
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColorOrArtifact;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class RazorBarrier extends CardImpl {

    public RazorBarrier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");


        // Target permanent you control gains protection from artifacts or from the color of your choice until end of turn.
        this.getSpellAbility().addEffect(new RazorBarrierEffect(Duration.EndOfTurn));
        Target target = new TargetControlledPermanent();
        this.getSpellAbility().addTarget(target);
    }

    public RazorBarrier(final RazorBarrier card) {
        super(card);
    }

    @Override
    public RazorBarrier copy() {
        return new RazorBarrier(this);
    }
}

class RazorBarrierEffect extends GainAbilityTargetEffect {

    public RazorBarrierEffect(Duration duration) {
        super(new ProtectionAbility(new FilterCard()), duration);
        staticText = "Target permanent you control gains protection from artifacts or from the color of your choice until end of turn";
    }

    public RazorBarrierEffect(final RazorBarrierEffect effect) {
        super(effect);
    }

    @Override
    public RazorBarrierEffect copy() {
        return new RazorBarrierEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            FilterCard protectionFilter = new FilterCard();
            ChoiceColorOrArtifact choice = new ChoiceColorOrArtifact();
            if (controller.choose(outcome, choice, game)) {
                if (choice.isArtifactSelected()) {
                    protectionFilter.add(new CardTypePredicate(CardType.ARTIFACT));
                } else {
                    protectionFilter.add(new ColorPredicate(choice.getColor()));
                }

                protectionFilter.setMessage(choice.getChoice());
                ((ProtectionAbility) ability).setFilter(protectionFilter);
                Permanent creature = game.getPermanent(source.getFirstTarget());
                if (creature != null) {
                    creature.addAbility(ability, source.getSourceId(), game);
                    return true;
                }
            }
        }
        return false;
    }

}

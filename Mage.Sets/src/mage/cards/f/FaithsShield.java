
package mage.cards.f;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.condition.common.FatefulHourCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControllerEffect;
import mage.abilities.effects.common.continuous.GainProtectionFromColorTargetEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author BetaSteward
 */
public final class FaithsShield extends CardImpl {

    public FaithsShield(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Target permanent you control gains protection from the color of your choice until end of turn.
        // Fateful hour - If you have 5 or less life, instead you and each permanent you control gain protection from the color of your choice until end of turn.
        this.getSpellAbility().addEffect(new FaithsShieldEffect());
        this.getSpellAbility().addTarget(new TargetControlledPermanent());
    }

    private FaithsShield(final FaithsShield card) {
        super(card);
    }

    @Override
    public FaithsShield copy() {
        return new FaithsShield(this);
    }
}

class FaithsShieldEffect extends OneShotEffect {

    public FaithsShieldEffect() {
        super(Outcome.Protect);
        staticText = "Target permanent you control gains protection from the color of your choice until end of turn."
                + "<br/><br/><i>Fateful hour</i> &mdash; If you have 5 or less life, instead you and each permanent you control gain protection from the color of your choice until end of turn";
    }

    public FaithsShieldEffect(final FaithsShieldEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getObject(source);
        if (controller != null && mageObject != null) {
            if (FatefulHourCondition.instance.apply(game, source)) {
                ChoiceColor choice = new ChoiceColor();
                if (!controller.choose(Outcome.Protect, choice, game)) {
                    return false;
                }
                if (choice.getColor() != null) {
                    game.informPlayers(mageObject.getLogName() + ": " + controller.getLogName() + " has chosen " + choice.getChoice());
                    FilterCard filter = new FilterCard();
                    filter.add(new ColorPredicate(choice.getColor()));
                    filter.setMessage(choice.getChoice());
                    Ability ability = new ProtectionAbility(filter);
                    game.addEffect(new GainAbilityControlledEffect(ability, Duration.EndOfTurn), source);
                    game.addEffect(new GainAbilityControllerEffect(ability, Duration.EndOfTurn), source);
                    return true;
                }
            } else {
                game.addEffect(new GainProtectionFromColorTargetEffect(Duration.EndOfTurn), source);
                return true;
            }

        }
        return false;
    }

    @Override
    public FaithsShieldEffect copy() {
        return new FaithsShieldEffect(this);
    }

}

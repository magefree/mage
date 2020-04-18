
package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.keyword.ProtectionAbility;
import mage.choices.ChoiceColor;
import mage.constants.Duration;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class GainProtectionFromColorAttachedEffect extends GainAbilitySourceEffect {

    FilterCard protectionFilter;

    public GainProtectionFromColorAttachedEffect(Duration duration) {
        super(new ProtectionAbility(new FilterCard()), duration);
        protectionFilter = (FilterCard) ((ProtectionAbility) ability).getFilter();
    }

    public GainProtectionFromColorAttachedEffect(final GainProtectionFromColorAttachedEffect effect) {
        super(effect);
        this.protectionFilter = effect.protectionFilter.copy();
    }

    @Override
    public GainProtectionFromColorAttachedEffect copy() {
        return new GainProtectionFromColorAttachedEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            ChoiceColor colorChoice = new ChoiceColor(true);
            colorChoice.setMessage("Choose color for protection ability");
            if (controller.choose(outcome, colorChoice, game)) {
                game.informPlayers("Choosen color: " + colorChoice.getColor());
                protectionFilter.add(new ColorPredicate(colorChoice.getColor()));
                protectionFilter.setMessage(colorChoice.getChoice());
                ((ProtectionAbility) ability).setFilter(protectionFilter);
                return;
            }
        }
        discard();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null && permanent.getAttachedTo() != null) {
            Permanent attachedPermanent = game.getPermanent(permanent.getAttachedTo());
            attachedPermanent.addAbility(ability, source.getSourceId(), game);
        } else {
            // the source permanent is no longer on the battlefield, or it is not attached -- effect can be discarded
            discard();
        }
        return true;
    }
}

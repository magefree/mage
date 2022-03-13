
package mage.abilities.effects.common.continuous;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.keyword.ProtectionAbility;
import mage.choices.ChoiceColor;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LoneFox
 */
public class GainProtectionFromColorAllEffect extends GainAbilityAllEffect {

    protected ChoiceColor choice;

    public GainProtectionFromColorAllEffect(Duration duration, FilterPermanent filter) {
        super(new ProtectionAbility(new FilterCard()), duration, filter);
        choice = new ChoiceColor(true);
    }

    public GainProtectionFromColorAllEffect(final GainProtectionFromColorAllEffect effect) {
        super(effect);
        this.choice = effect.choice.copy();
    }

    @Override
    public GainProtectionFromColorAllEffect copy() {
        return new GainProtectionFromColorAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterCard protectionFilter = (FilterCard) ((ProtectionAbility) ability).getFilter();
        protectionFilter.add(new ColorPredicate(choice.getColor()));
        protectionFilter.setMessage(choice.getChoice());
        ((ProtectionAbility) ability).setFilter(protectionFilter);
        return super.apply(game, source);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        MageObject sourceObject = game.getObject(source);
        Player controller = game.getPlayer(source.getControllerId());
        if (sourceObject != null && controller != null) {
            if (!controller.choose(Outcome.Protect, choice, game)) {
                discard();
                return;
            }
            game.informPlayers(sourceObject.getLogName() + ": " + controller.getLogName() + " has chosen protection from " + choice.getChoice());
        }
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }

        String text = "Choose a color. " + filter.getMessage() + " gain protection from the chosen color " + duration.toString();

        return text;
    }
}

package mage.abilities.effects.common.continuous;

import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.keyword.ProtectionAbility;
import mage.choices.ChoiceColor;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class GainProtectionFromColorTargetEffect extends GainAbilityTargetEffect {

    protected ChoiceColor choice;

    public GainProtectionFromColorTargetEffect(Duration duration) {
        this(duration, null);
    }

    public GainProtectionFromColorTargetEffect(Duration duration, ObjectColor protectColor) {
        super(new ProtectionAbility(new FilterCard()), duration);
        choice = new ChoiceColor(true, "Choose a color to gain protection against it");
        if (protectColor != null) {
            choice.setChoice(protectColor.toString());
        }
    }

    protected GainProtectionFromColorTargetEffect(final GainProtectionFromColorTargetEffect effect) {
        super(effect);
        this.choice = effect.choice.copy();
    }

    @Override
    public GainProtectionFromColorTargetEffect copy() {
        return new GainProtectionFromColorTargetEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        MageObject sourceObject = game.getObject(source);
        Player controller = game.getPlayer(source.getControllerId());
        if (sourceObject != null && controller != null) {
            if (controller.choose(Outcome.Protect, choice, game)) {
                game.informPlayers(sourceObject.getLogName() + ": " + controller.getLogName() + " has chosen protection from " + choice.getChoice());
                return;
            }
        }
        discard();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (creature != null) {
            FilterCard protectionFilter = (FilterCard) ((ProtectionAbility) ability).getFilter();
            protectionFilter.add(new ColorPredicate(choice.getColor()));
            protectionFilter.setMessage(choice.getChoice());
            ((ProtectionAbility) ability).setFilter(protectionFilter);
            creature.addAbility(ability, source.getSourceId(), game);
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return getTargetPointer().describeTargets(mode.getTargets(), "it")
                + " gains protection from the color of your choice " + duration.toString();
    }
}

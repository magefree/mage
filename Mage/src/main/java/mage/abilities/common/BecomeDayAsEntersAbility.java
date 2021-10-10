package mage.abilities.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;

/**
 * @author TheElk801
 * TODO: this is just a placeholder for the actual ability
 */
public class BecomeDayAsEntersAbility extends EntersBattlefieldAbility {

    public BecomeDayAsEntersAbility() {
        super(new BecomeDayEffect());
    }

    private BecomeDayAsEntersAbility(final BecomeDayAsEntersAbility ability) {
        super(ability);
    }

    @Override
    public BecomeDayAsEntersAbility copy() {
        return new BecomeDayAsEntersAbility(this);
    }

    @Override
    public String getRule() {
        return "If it's neither day nor night, it becomes day as {this} enters the battlefield.";
    }
}

class BecomeDayEffect extends OneShotEffect {

    BecomeDayEffect() {
        super(Outcome.Benefit);
    }

    private BecomeDayEffect(final BecomeDayEffect effect) {
        super(effect);
    }

    @Override
    public BecomeDayEffect copy() {
        return new BecomeDayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }
}

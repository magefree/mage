
package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.command.Emblem;

import java.util.List;

/**
 * @author nantuko
 */
public class GetEmblemEffect extends OneShotEffect {

    private final Emblem emblem;

    public GetEmblemEffect(Emblem emblem) {
        super(Outcome.Benefit);
        this.emblem = emblem;
        this.staticText = getText();
    }

    public GetEmblemEffect(final GetEmblemEffect effect) {
        super(effect);
        this.emblem = effect.emblem;
    }

    @Override
    public GetEmblemEffect copy() {
        return new GetEmblemEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = game.getObject(source);
        if (sourceObject == null) {
            return false;
        }
        game.addEmblem(emblem, sourceObject, source);
        return true;
    }

    public String getText() {
        StringBuilder sb = new StringBuilder();
        sb.append("you get an emblem with \"");
        List<String> rules = emblem.getAbilities().getRules(null);
        sb.append(rules.get(0));
        if (rules.size() == 2) {
            sb.append("\" and \"");
            sb.append(rules.get(1));
        }
        sb.append('"');
        return sb.toString();
    }
}

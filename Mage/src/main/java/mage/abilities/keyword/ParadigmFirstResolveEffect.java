package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.stack.Spell;

/**
 * Marks the first resolved Paradigm spell for a controller/name pair.
 * This is a spell effect so spell copies can establish the recurring Paradigm
 * trigger before the original physical card finishes resolving.
 */
public class ParadigmFirstResolveEffect extends OneShotEffect {

    public ParadigmFirstResolveEffect() {
        super(Outcome.Benefit);
        staticText = "";
    }

    private ParadigmFirstResolveEffect(final ParadigmFirstResolveEffect effect) {
        super(effect);
    }

    @Override
    public ParadigmFirstResolveEffect copy() {
        return new ParadigmFirstResolveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = (Spell) source.getSourceObject(game);
        if (spell == null) {
            return false;
        }
        Card card = spell.getCard();
        if (card == null) {
            return false;
        }
        String key = ParadigmUtil.getKey(source.getControllerId(), card.getName());
        if (Boolean.TRUE.equals(game.getState().getValue(key))) {
            return true;
        }
        game.getState().setValue(key, Boolean.TRUE);
        game.getState().setValue(
                ParadigmUtil.getFirstSourceKey(source.getControllerId(), card.getName()),
                card.getId()
        );
        game.addDelayedTriggeredAbility(new ParadigmDelayedTriggeredAbility(card, game), source);
        return true;
    }
}

package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TemptWithMayhem extends CardImpl {

    public TemptWithMayhem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}{R}");

        // Tempting offer -- Choose target instant or sorcery spell. Each opponent may copy that spell and may choose new targets for the copy they control. You copy that spell once plus an additional time for each opponent who copied the spell this way. You may choose new targets for the copies you control.
        this.getSpellAbility().addEffect(new TemptWithMayhemEffect());
        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_INSTANT_OR_SORCERY));
        this.getSpellAbility().setAbilityWord(AbilityWord.TEMPTING_OFFER);
    }

    private TemptWithMayhem(final TemptWithMayhem card) {
        super(card);
    }

    @Override
    public TemptWithMayhem copy() {
        return new TemptWithMayhem(this);
    }
}

class TemptWithMayhemEffect extends OneShotEffect {

    TemptWithMayhemEffect() {
        super(Outcome.Benefit);
        staticText = "choose target instant or sorcery spell. Each opponent may copy that spell " +
                "and may choose new targets for the copy they control. You copy that spell once " +
                "plus an additional time for each opponent who copied the spell this way. " +
                "You may choose new targets for the copies you control";
    }

    private TemptWithMayhemEffect(final TemptWithMayhemEffect effect) {
        super(effect);
    }

    @Override
    public TemptWithMayhemEffect copy() {
        return new TemptWithMayhemEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getSpell(getTargetPointer().getFirst(game, source));
        if (spell == null) {
            return false;
        }
        int count = 0;
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent != null && opponent.chooseUse(
                    Outcome.Copy, "Copy " + spell.getIdName() + '?', source, game
            )) {
                spell.createCopyOnStack(game, source, opponentId, true);
                count++;
            }
        }
        spell.createCopyOnStack(game, source, source.getControllerId(), true, count + 1);
        return true;
    }
}

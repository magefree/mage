package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GloriousGale extends CardImpl {

    public GloriousGale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Counter target creature spell. If it was a legendary spell, the Ring tempts you.
        this.getSpellAbility().addEffect(new GloriousGaleEffect());
        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_CREATURE));
    }

    private GloriousGale(final GloriousGale card) {
        super(card);
    }

    @Override
    public GloriousGale copy() {
        return new GloriousGale(this);
    }
}

class GloriousGaleEffect extends OneShotEffect {

    GloriousGaleEffect() {
        super(Outcome.Benefit);
        staticText = "counter target creature spell. If it was a legendary spell, the Ring tempts you";
    }

    private GloriousGaleEffect(final GloriousGaleEffect effect) {
        super(effect);
    }

    @Override
    public GloriousGaleEffect copy() {
        return new GloriousGaleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getSpell(getTargetPointer().getFirst(game, source));
        if (spell == null) {
            return false;
        }
        game.getStack().counter(spell.getId(), source, game);
        if (spell.isLegendary(game)) {
            game.temptWithTheRing(source.getControllerId());
        }
        return true;
    }
}

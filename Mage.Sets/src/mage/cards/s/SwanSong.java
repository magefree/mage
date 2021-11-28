
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.token.SwanSongBirdToken;
import mage.game.permanent.token.Token;
import mage.game.stack.Spell;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public final class SwanSong extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("enchantment, instant, or sorcery spell");

    static {
        filter.add(Predicates.or(CardType.ENCHANTMENT.getPredicate(),
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()));
    }

    public SwanSong(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Counter target enchantment, instant or sorcery spell. Its controller creates a 2/2 blue Bird creature token with flying.
        this.getSpellAbility().addEffect(new SwanSongEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filter));
    }

    private SwanSong(final SwanSong card) {
        super(card);
    }

    @Override
    public SwanSong copy() {
        return new SwanSong(this);
    }
}

class SwanSongEffect extends OneShotEffect {

    public SwanSongEffect() {
        super(Outcome.Benefit);
        this.staticText = "Counter target enchantment, instant, or sorcery spell. Its controller creates a 2/2 blue Bird creature token with flying";
    }

    public SwanSongEffect(final SwanSongEffect effect) {
        super(effect);
    }

    @Override
    public SwanSongEffect copy() {
        return new SwanSongEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean countered = false;
        for (UUID targetId : targetPointer.getTargets(game, source)) {
            Spell spell = game.getStack().getSpell(targetId);
            if (game.getStack().counter(targetId, source, game)) {
                countered = true;
            }
            if (spell != null) {
                Token token = new SwanSongBirdToken();
                token.putOntoBattlefield(1, game, source, spell.getControllerId());
            }
        }
        return countered;
    }
}

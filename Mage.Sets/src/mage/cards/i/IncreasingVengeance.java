
package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author BetaSteward
 */
public final class IncreasingVengeance extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("instant or sorcery spell");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()));
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public IncreasingVengeance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}{R}");

        // Copy target instant or sorcery spell you control. If this spell was cast from a graveyard, copy that spell twice instead. You may choose new targets for the copies.
        this.getSpellAbility().addEffect(new IncreasingVengeanceEffect());
        Target target = new TargetSpell(filter);
        this.getSpellAbility().addTarget(target);

        // Flashback {3}{R}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{3}{R}{R}")));
    }

    private IncreasingVengeance(final IncreasingVengeance card) {
        super(card);
    }

    @Override
    public IncreasingVengeance copy() {
        return new IncreasingVengeance(this);
    }
}

class IncreasingVengeanceEffect extends OneShotEffect {

    public IncreasingVengeanceEffect() {
        super(Outcome.BoostCreature);
        staticText = "Copy target instant or sorcery spell you control. If this spell was cast from a graveyard, copy that spell twice instead. You may choose new targets for the copies";
    }

    public IncreasingVengeanceEffect(final IncreasingVengeanceEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Spell spell = game.getStack().getSpell(targetPointer.getFirst(game, source));
        if (spell == null) {
            return false;
        }
        Spell sourceSpell = (Spell) game.getSpell(source.getSourceId());
        int copies = 1;
        if (sourceSpell != null && sourceSpell.getFromZone() == Zone.GRAVEYARD) {
            copies++;
        }
        spell.createCopyOnStack(game, source, source.getControllerId(), true, copies);
        return true;
    }

    @Override
    public IncreasingVengeanceEffect copy() {
        return new IncreasingVengeanceEffect(this);
    }

}

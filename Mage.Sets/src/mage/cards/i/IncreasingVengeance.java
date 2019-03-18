
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetSpell;

/**
 *
 * @author BetaSteward
 */
public final class IncreasingVengeance extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("instant or sorcery spell");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public IncreasingVengeance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}{R}");

        // Copy target instant or sorcery spell you control. If this spell was cast from a graveyard, copy that spell twice instead. You may choose new targets for the copies.
        this.getSpellAbility().addEffect(new IncreasingVengeanceEffect());
        Target target = new TargetSpell(filter);
        this.getSpellAbility().addTarget(target);

        // Flashback {3}{R}{R}
        this.addAbility(new FlashbackAbility(new ManaCostsImpl("{3}{R}{R}"), TimingRule.INSTANT));
    }

    public IncreasingVengeance(final IncreasingVengeance card) {
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
        if (controller != null) {
            Spell spell = game.getStack().getSpell(targetPointer.getFirst(game, source));
            if (spell != null) {
                StackObject stackObjectCopy = spell.createCopyOnStack(game, source, source.getControllerId(), true);
                if (stackObjectCopy instanceof Spell) {
                    game.informPlayers(controller.getLogName() + ((Spell) stackObjectCopy).getActivatedMessage(game));
                }
                Spell sourceSpell = (Spell) game.getStack().getStackObject(source.getSourceId());
                if (sourceSpell != null) {
                    if (sourceSpell.getFromZone() == Zone.GRAVEYARD) {
                        stackObjectCopy = spell.createCopyOnStack(game, source, source.getControllerId(), true);
                        if (stackObjectCopy instanceof Spell) {
                            game.informPlayers(new StringBuilder(controller.getLogName()).append(((Spell) stackObjectCopy).getActivatedMessage(game)).toString());
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public IncreasingVengeanceEffect copy() {
        return new IncreasingVengeanceEffect(this);
    }

}

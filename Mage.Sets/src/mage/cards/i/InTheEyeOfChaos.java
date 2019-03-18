
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public final class InTheEyeOfChaos extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an instant spell");
    static {
            filter.add(new CardTypePredicate(CardType.INSTANT));
    }

    public InTheEyeOfChaos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}");
        addSuperType(SuperType.WORLD);


        // Whenever a player casts an instant spell, counter it unless that player pays {X}, where X is its converted mana cost.
        this.addAbility(new SpellCastAllTriggeredAbility(Zone.BATTLEFIELD, new InTheEyeOfChaosEffect(), filter, false, SetTargetPointer.SPELL));
    }

    public InTheEyeOfChaos(final InTheEyeOfChaos card) {
        super(card);
    }

    @Override
    public InTheEyeOfChaos copy() {
        return new InTheEyeOfChaos(this);
    }
}

class InTheEyeOfChaosEffect extends OneShotEffect {

    InTheEyeOfChaosEffect() {
        super(Outcome.Detriment);
        this.staticText = "counter it unless that player pays {X}, where X is its converted mana cost";
    }

    InTheEyeOfChaosEffect(final InTheEyeOfChaosEffect effect) {
        super(effect);
    }

    @Override
    public InTheEyeOfChaosEffect copy() {
        return new InTheEyeOfChaosEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject spell = game.getStack().getStackObject(targetPointer.getFirst(game, source));
        if (spell != null) {
            Player player = game.getPlayer(spell.getControllerId());
            if (player != null) {
                GenericManaCost cost = new GenericManaCost(spell.getConvertedManaCost());
                if (!cost.pay(source, game, source.getSourceId(), player.getId(), false)) {
                    game.getStack().counter(spell.getId(), source.getSourceId(), game);
                }
                return true;
            }
        }
        return false;
    }
}

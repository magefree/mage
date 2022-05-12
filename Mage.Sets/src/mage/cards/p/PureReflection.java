
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureSpell;
import mage.game.Game;
import mage.game.permanent.token.ReflectionPureToken;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 *
 * @author SpikesCafe-google
 */
public final class PureReflection extends CardImpl {

    public PureReflection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");
        // Whenever a player casts a creature spell, destroy all Reflections. Then that player creates an X/X white Reflection creature token, where X is the converted mana cost of that spell.
        this.addAbility(new SpellCastAllTriggeredAbility(new PureReflectionEffect(), new FilterCreatureSpell(), false, SetTargetPointer.SPELL));
    }

    private PureReflection(final PureReflection card) {
        super(card);
    }

    @Override
    public PureReflection copy() {
        return new PureReflection(this);
    }

    private class PureReflectionEffect extends OneShotEffect {

        public PureReflectionEffect() {
            super(Outcome.Benefit);
            staticText = "destroy all Reflections. Then that player creates an X/X white Reflection creature token, where X is the mana value of that spell.";
        }

        public PureReflectionEffect(PureReflectionEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Player controller = game.getPlayer(source.getControllerId());

            if (controller == null || game.getPermanentOrLKIBattlefield(source.getSourceId()) == null) {
                return false;
            }

            Spell spell = game.getSpellOrLKIStack(this.getTargetPointer().getFirst(game, source));

            if (spell == null) {
                return false;
            }

            // destroy all Reflections
            FilterPermanent filter = new FilterPermanent("Reflections");
            filter.add(SubType.REFLECTION.getPredicate());
            game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game).forEach((permanent) -> {
                permanent.destroy(source, game,false);
            });
            game.getState().processAction(game);
            
            // Then that player creates an X/X white Reflection creature token, where X is the converted mana cost of that spell.
            ReflectionPureToken token = new ReflectionPureToken(spell.getManaValue());
            token.putOntoBattlefield(1, game, source, spell.getControllerId());

            return true;
        }

        @Override
        public Effect copy() {
            return new PureReflectionEffect(this);
        }
    }
}

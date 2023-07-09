
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class Counterbalance extends CardImpl {

    public Counterbalance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{U}{U}");


        // Whenever an opponent casts a spell, you may reveal the top card of your library. If you do, counter that spell if it has the same converted mana cost as the revealed card.
        this.addAbility(new SpellCastOpponentTriggeredAbility(Zone.BATTLEFIELD, new CounterbalanceEffect(), StaticFilters.FILTER_SPELL_A, true, SetTargetPointer.SPELL));
    }

    private Counterbalance(final Counterbalance card) {
        super(card);
    }

    @Override
    public Counterbalance copy() {
        return new Counterbalance(this);
    }
}

class CounterbalanceEffect extends OneShotEffect {

    public CounterbalanceEffect() {
        super(Outcome.Neutral);
        this.staticText = "you may reveal the top card of your library. If you do, counter that spell if it has the same mana value as the revealed card";
    }

    public CounterbalanceEffect(final CounterbalanceEffect effect) {
        super(effect);
    }

    @Override
    public CounterbalanceEffect copy() {
        return new CounterbalanceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            Spell spell = (Spell) game.getStack().getStackObject(targetPointer.getFirst(game, source));
            if (spell != null) {
                Card topcard = controller.getLibrary().getFromTop(game);
                if (topcard != null) {
                    CardsImpl cards = new CardsImpl();
                    cards.add(topcard);
                    controller.revealCards(sourcePermanent.getName(), cards, game);
                    if (topcard.getManaValue() == spell.getManaValue()) {
                        return game.getStack().counter(spell.getId(), source, game);
                    }
                }
                return true;
            }
        }
        return false;
    }


}


package mage.cards.u;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author cbt33, Level_X2 (Horn of Plenty)
 */

public final class UnifyingTheory extends CardImpl {

    public UnifyingTheory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{U}");


        // Whenever a player casts a spell, that player may pay {2}. If the player does, they draw a card.
        this.addAbility(new SpellCastAllTriggeredAbility(new UnifyingTheoryEffect() , StaticFilters.FILTER_SPELL_A, false, SetTargetPointer.PLAYER));
    }

    private UnifyingTheory(final UnifyingTheory card) {
        super(card);
    }

    @Override
    public UnifyingTheory copy() {
        return new UnifyingTheory(this);
    }
}

class UnifyingTheoryEffect extends OneShotEffect {

    public UnifyingTheoryEffect() {
        super(Outcome.Detriment);
        this.staticText = "that player may pay {2}. If the player does, they draw a card";
    }

    public UnifyingTheoryEffect(final UnifyingTheoryEffect effect) {
        super(effect);
    }

    @Override
    public UnifyingTheoryEffect copy() {
        return new UnifyingTheoryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player caster = game.getPlayer(targetPointer.getFirst(game, source));
        if (caster != null) {
            if (caster.chooseUse(Outcome.DrawCard, "Pay {2} to draw a card?", source, game)) {
                Cost cost = new ManaCostsImpl<>("{2}");
                if (cost.pay(source, game, source, caster.getId(), false, null)) {
                    caster.drawCards(1, source, game);
                }
            }
            return true;
        }
        return false;
    }
}

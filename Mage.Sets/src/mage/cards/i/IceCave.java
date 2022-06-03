
package mage.cards.i;

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
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 *
 * @author ThomasLerner
 */
public final class IceCave extends CardImpl {

    public IceCave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}{U}");

        // Whenever a player casts a spell, any other player may pay that spell's mana cost. If a player does, counter the spell. (Mana cost includes color.)
        this.addAbility(new SpellCastAllTriggeredAbility(Zone.BATTLEFIELD, new IceCaveEffect(), StaticFilters.FILTER_SPELL_A, false, SetTargetPointer.SPELL));
    }

    private IceCave(final IceCave card) {
        super(card);
    }

    @Override
    public IceCave copy() {
        return new IceCave(this);
    }
}

class IceCaveEffect extends OneShotEffect {

    public IceCaveEffect() {
        super(Outcome.AIDontUseIt);
        this.staticText = "any other player may pay that spell's mana cost. If a player does, counter the spell";
    }

    public IceCaveEffect(final IceCaveEffect effect) {
        super(effect);
    }

    @Override
    public IceCaveEffect copy() {
        return new IceCaveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        Spell spell = (Spell) game.getStack().getStackObject(targetPointer.getFirst(game, source));
        if (sourcePermanent != null && spell != null && controller != null) {
            Player spellController = game.getPlayer(spell.getControllerId());
            Cost cost = new ManaCostsImpl<>(spell.getSpellAbility().getManaCosts().getText());
            if (spellController != null) {
                for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                    Player player = game.getPlayer(playerId);
                    if (player != null && !player.equals(spellController)) {
                        cost.clearPaid();
                        if (cost.canPay(source, source, player.getId(), game)
                                && player.chooseUse(outcome, "Pay " + cost.getText() + " to counter " + spell.getIdName() + '?', source, game)) {
                            if (cost.pay(source, game, source, playerId, false, null)) {
                                game.informPlayers(player.getLogName() + " pays" + cost.getText() + " to counter " + spell.getIdName() + '.');
                                game.getStack().counter(spell.getId(), source, game);
                                break;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}

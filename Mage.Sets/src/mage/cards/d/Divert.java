
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author cbt33, Rafbill (Frightful Delustions)
 */
public final class Divert extends CardImpl {

    public Divert(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");


        // Change the target of target spell with a single target unless that spell's controller pays {2}.
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new DivertEffect());
    }

    public Divert(final Divert card) {
        super(card);
    }

    @Override
    public Divert copy() {
        return new Divert(this);
    }
}

class DivertEffect extends OneShotEffect {

    public DivertEffect() {
        super(Outcome.Detriment);
        this.staticText = "Change the target of target spell with a single target unless that spell's controller pays {2}.";
    }

    public DivertEffect(final DivertEffect effect) {
        super(effect);
    }

    @Override
    public DivertEffect copy() {
        return new DivertEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(source.getFirstTarget());
        Cost cost = new GenericManaCost(2);
        if (spell != null) {
            Player player = game.getPlayer(spell.getControllerId());
            if (player != null) {
                cost.clearPaid();
                if (!cost.pay(source, game, spell.getControllerId(),
                        spell.getControllerId(), false, null)) {
                    return spell.chooseNewTargets(game, source.getControllerId(), true, true, null);
                }
            }
        }
        return false;
    }

}

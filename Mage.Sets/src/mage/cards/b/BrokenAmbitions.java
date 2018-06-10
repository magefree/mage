
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ClashEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author fireshoes
 */
public final class BrokenAmbitions extends CardImpl {

    public BrokenAmbitions(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{U}");

        // Counter target spell unless its controller pays {X}. Clash with an opponent. If you win, that spell's controller puts the top four cards of their library into their graveyard.
        this.getSpellAbility().addEffect(new BrokenAmbitionsEffect(new ManacostVariableValue()));
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    public BrokenAmbitions(final BrokenAmbitions card) {
        super(card);
    }

    @Override
    public BrokenAmbitions copy() {
        return new BrokenAmbitions(this);
    }
}

class BrokenAmbitionsEffect extends OneShotEffect {
    
    protected Cost cost;
    protected DynamicValue genericMana;

    public BrokenAmbitionsEffect(Cost cost) {
        super(Outcome.Benefit);
        this.cost = cost;
        this.staticText = "Counter target spell unless its controller pays {X}. Clash with an opponent. If you win, that spell's controller puts the top four cards of their library into their graveyard";
    }
    
    public BrokenAmbitionsEffect(DynamicValue genericMana) {
        super(Outcome.Detriment);
        this.genericMana = genericMana;
    }

    public BrokenAmbitionsEffect(final BrokenAmbitionsEffect effect) {
        super(effect);
        if (effect.cost != null) {
            this.cost = effect.cost.copy();
        }
        if (effect.genericMana != null) {
            this.genericMana = effect.genericMana.copy();
        }
    }

    @Override
    public BrokenAmbitionsEffect copy() {
        return new BrokenAmbitionsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = (Spell) game.getStack().getStackObject(getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(spell.getControllerId());
        if (controller != null) {
            Cost costToPay;
            if (cost != null) {
                    costToPay = cost.copy();
                } else  {
                    costToPay = new GenericManaCost(genericMana.calculate(game, source, this));
                }
                String message;
                if (costToPay instanceof ManaCost) {
                    message = "Would you like to pay " + costToPay.getText() + " to prevent counter effect?";
                } else {
                    message = costToPay.getText() + " to prevent counter effect?";
                }
                costToPay.clearPaid();
                if (!(controller.chooseUse(Outcome.Benefit, message, source, game) && costToPay.pay(source, game, spell.getSourceId(), spell.getControllerId(), false, null))) {
                    game.getStack().counter(spell.getId(), source.getSourceId(), game);
                }
                if (ClashEffect.getInstance().apply(game, source)) {
                    controller.moveCards(controller.getLibrary().getTopCards(game, 4), Zone.GRAVEYARD, source, game);
                }
            return true;
        }
        return false;
    }
}
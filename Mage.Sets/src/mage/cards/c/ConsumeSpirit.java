
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterMana;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;



/**
 * @author nantuko
 */
public final class ConsumeSpirit extends CardImpl {

    static final FilterMana filterBlack = new FilterMana();

    static {
        filterBlack.setBlack(true);
    }

    public ConsumeSpirit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{1}{B}");


        // Spend only black mana on X.
        // Consume Spirit deals X damage to any target and you gain X life.
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new ConsumeSpiritEffect());
        VariableCost variableCost = this.getSpellAbility().getManaCostsToPay().getVariableCosts().get(0);
        if (variableCost instanceof VariableManaCost) {
            ((VariableManaCost) variableCost).setFilter(filterBlack);
        }
    }

    public ConsumeSpirit(final ConsumeSpirit card) {
        super(card);
    }

    @Override
    public ConsumeSpirit copy() {
        return new ConsumeSpirit(this);
    }
}

class ConsumeSpiritEffect extends OneShotEffect {

    public ConsumeSpiritEffect() {
        super(Outcome.Damage);
        staticText = "Consume Spirit deals X damage to any target and you gain X life. Spend only black mana on X";
    }

    public ConsumeSpiritEffect(final ConsumeSpiritEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = source.getManaCostsToPay().getX();
        if (amount > 0) {
            Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (permanent != null) {
                permanent.damage(amount, source.getSourceId(), game, false, true);
            } else {
                Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
                if (player != null) {
                    player.damage(amount, source.getSourceId(), game, false, true);
                } else {
                    return false;
                }
            }
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                controller.gainLife(amount, game, source);
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public ConsumeSpiritEffect copy() {
        return new ConsumeSpiritEffect(this);
    }

}

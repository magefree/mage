
package mage.cards.d;

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
 *
 * @author KholdFuzion
 *
 */
public final class DrainLife extends CardImpl {

    static final FilterMana filterBlack = new FilterMana();

    static {
        filterBlack.setBlack(true);
    }

    public DrainLife(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{1}{B}");

        // Spend only black mana on X.
        // Drain Life deals X damage to any target. You gain life equal to the damage dealt, but not more life than the player's life total before Drain Life dealt damage or the creature's toughness.
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new DrainLifeEffect());
        VariableCost variableCost = this.getSpellAbility().getManaCostsToPay().getVariableCosts().get(0);
        if (variableCost instanceof VariableManaCost) {
            ((VariableManaCost) variableCost).setFilter(filterBlack);
        }
    }

    public DrainLife(final DrainLife card) {
        super(card);
    }

    @Override
    public DrainLife copy() {
        return new DrainLife(this);
    }
}

class DrainLifeEffect extends OneShotEffect {

    public DrainLifeEffect() {
        super(Outcome.Damage);
        staticText = "Spend only black mana on X.<br>{this} deals X damage to any target. You gain life equal to the damage dealt, but not more life than the player's life total before Drain Life dealt damage or the creature's toughness";
    }

    public DrainLifeEffect(final DrainLifeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = source.getManaCostsToPay().getX();
        int lifetogain = amount;
        if (amount > 0) {
            Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (permanent != null) {
                if (permanent.getToughness().getValue() < amount) {
                    lifetogain = permanent.getToughness().getValue();
                }
                permanent.damage(amount, source.getSourceId(), game, false, true);
            } else {
                Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
                if (player != null) {
                    if (player.getLife() < amount) {
                        lifetogain = player.getLife();
                    }
                    player.damage(amount, source.getSourceId(), game, false, true);
                } else {
                    return false;
                }
            }
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                controller.gainLife(lifetogain, game, source);
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public DrainLifeEffect copy() {
        return new DrainLifeEffect(this);
    }

}

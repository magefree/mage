package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterMana;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author KholdFuzion
 */
public final class DrainLife extends CardImpl {

    private static final FilterMana filterBlack = new FilterMana();

    static {
        filterBlack.setBlack(true);
    }

    public DrainLife(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{1}{B}");

        // Spend only black mana on X.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new InfoEffect("Spend only black mana on X")).setRuleAtTheTop(true)
        );

        // Drain Life deals X damage to any target. You gain life equal to the damage dealt, but not more life than the player's life total before Drain Life dealt damage or the creature's toughness.
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new DrainLifeEffect());
        VariableCost variableCost = this.getSpellAbility().getManaCostsToPay().getVariableCosts().get(0);
        if (variableCost instanceof VariableManaCost) {
            ((VariableManaCost) variableCost).setFilter(filterBlack);
        }
    }

    private DrainLife(final DrainLife card) {
        super(card);
    }

    @Override
    public DrainLife copy() {
        return new DrainLife(this);
    }
}

class DrainLifeEffect extends OneShotEffect {

    DrainLifeEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals X damage to any target. You gain life equal to the damage dealt, " +
                "but not more life than the player's life total before the damage was dealt, " +
                "the planeswalker's loyalty before the damage was dealt, or the creature's toughness.";
    }

    private DrainLifeEffect(final DrainLifeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = source.getManaCostsToPay().getX();
        int lifetogain = amount;
        if (amount == 0) {
            return true;
        }
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            if (permanent.isCreature(game)) {
                lifetogain = Math.min(permanent.getToughness().getValue(), lifetogain);
            } else if (permanent.isPlaneswalker(game)) {
                lifetogain = Math.min(permanent.getCounters(game).getCount(CounterType.LOYALTY), lifetogain);
            } else {
                return false;
            }
            permanent.damage(amount, source.getSourceId(), source, game);
        } else {
            Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
            if (player == null) {
                return false;
            }
            lifetogain = Math.min(player.getLife(), lifetogain);
            player.damage(amount, source.getSourceId(), source, game);
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        controller.gainLife(lifetogain, game, source);
        return true;
    }

    @Override
    public DrainLifeEffect copy() {
        return new DrainLifeEffect(this);
    }

}

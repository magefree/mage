
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToACreatureTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class PlagueFiend extends CardImpl {

    public PlagueFiend(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Plague Fiend deals combat damage to a creature, destroy that creature unless its controller pays {2}.
        this.addAbility(new DealsCombatDamageToACreatureTriggeredAbility(new PlagueFiendEffect(new GenericManaCost(2)), false, true));
    }

    private PlagueFiend(final PlagueFiend card) {
        super(card);
    }

    @Override
    public PlagueFiend copy() {
        return new PlagueFiend(this);
    }
}

class PlagueFiendEffect extends OneShotEffect {

    protected Cost cost;

    public PlagueFiendEffect(Cost cost) {
        super(Outcome.Detriment);
        this.staticText = "destroy that creature unless its controller pays {2}";
        this.cost = cost;
    }

    private PlagueFiendEffect(final PlagueFiendEffect effect) {
        super(effect);
        this.cost = effect.cost.copy();
    }

    @Override
    public PlagueFiendEffect copy() {
        return new PlagueFiendEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (targetCreature != null) {
                Player player = game.getPlayer(targetCreature.getControllerId());
                if (player != null) {
                    cost.clearPaid();
                    final StringBuilder sb = new StringBuilder("Pay {2}? (Otherwise ").append(targetCreature.getName()).append(" will be destroyed)");
                    if (player.chooseUse(Outcome.Benefit, sb.toString(), source, game)) {
                        cost.pay(source, game, source, targetCreature.getControllerId(), false, null);
                    }
                    if (!cost.isPaid()) {
                        targetCreature.destroy(source, game, false);
                    }
                }
            }
            return true;
        }
        return false;
    }
}

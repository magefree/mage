
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.PayCostToAttackBlockEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class FloodedWoodlands extends CardImpl {

    public FloodedWoodlands(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{B}");
        

        // Green creatures can't attack unless their controller sacrifices a land for each green creature they control that's attacking.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new FloodedWoodlandsCostToAttackBlockEffect()));
        
    }

    private FloodedWoodlands(final FloodedWoodlands card) {
        super(card);
    }

    @Override
    public FloodedWoodlands copy() {
        return new FloodedWoodlands(this);
    }
}

class FloodedWoodlandsCostToAttackBlockEffect extends PayCostToAttackBlockEffectImpl {

    FloodedWoodlandsCostToAttackBlockEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, RestrictType.ATTACK,
                new SacrificeTargetCost(new TargetControlledPermanent(new FilterControlledLandPermanent("a land"))));
        staticText = "Green creatures can't attack unless their controller sacrifices a land <i>(This cost is paid as attackers are declared.)</i>";
    }

    private FloodedWoodlandsCostToAttackBlockEffect(final FloodedWoodlandsCostToAttackBlockEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(event.getSourceId());
        return (permanent != null
                && permanent.isCreature(game)
                && permanent.getColor(game).isGreen());
    }

    @Override
    public FloodedWoodlandsCostToAttackBlockEffect copy() {
        return new FloodedWoodlandsCostToAttackBlockEffect(this);
    }

}

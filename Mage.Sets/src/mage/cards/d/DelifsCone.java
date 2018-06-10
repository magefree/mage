
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAndIsNotBlockedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.AssignNoCombatDamageSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author MarcoMarin
 */
public final class DelifsCone extends CardImpl {

    public DelifsCone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{0}");

        Ability ability2 = new AttacksAndIsNotBlockedTriggeredAbility(new DelifsConeEffect(), true);
        ability2.addEffect(new AssignNoCombatDamageSourceEffect(Duration.EndOfTurn));
        // {tap}, Sacrifice Delif's Cone: This turn, when target creature you control attacks and isn't blocked, you may gain life equal to its power. If you do, it assigns no combat damage this turn.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new GainAbilityTargetEffect(ability2, Duration.EndOfTurn),
                new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetControlledCreaturePermanent());
        
        this.addAbility(ability);
        
    }

    public DelifsCone(final DelifsCone card) {
        super(card);
    }

    @Override
    public DelifsCone copy() {
        return new DelifsCone(this);
    }
}
class DelifsConeEffect extends OneShotEffect{
                      
    public DelifsConeEffect() {
        super(Outcome.Damage);
        this.setText("you may gain life equal to its power");
    }
    
    public DelifsConeEffect(final DelifsConeEffect effect) {
        super(effect);
    }
    
    @Override
    public DelifsConeEffect copy() {
        return new DelifsConeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent perm = game.getPermanent(source.getSourceId());
        GainLifeEffect lifeEffect = new GainLifeEffect(perm.getPower().getValue());
        return lifeEffect.apply(game, source);        
    }
}
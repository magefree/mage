package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author nick.myers
 */

public final class AuraThief extends CardImpl {
    
    public AuraThief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // When Aura Thief dies, you gain control of all enchantments. You don't get
        // to move Auras.
        this.addAbility(new DiesSourceTriggeredAbility(new AuraThiefDiesTriggeredEffect()));
    }
    
    private AuraThief(final AuraThief card) {
        super(card);
    }
    
    @Override
    public AuraThief copy() {
        return new AuraThief(this);
    }
    
}

class AuraThiefDiesTriggeredEffect extends OneShotEffect {
    
    public AuraThiefDiesTriggeredEffect() {
        super(Outcome.Benefit);
        this.staticText = "gain control of all enchantments. <i>(You don't get to move Auras.)</i>";
    }
    
    public AuraThiefDiesTriggeredEffect(final AuraThiefDiesTriggeredEffect effect) {
        super(effect);
    }
    
    @Override
    public AuraThiefDiesTriggeredEffect copy() {
        return new AuraThiefDiesTriggeredEffect(this);
    }
    
    @Override 
    public boolean apply(Game game, Ability source) {
        boolean ret = false;
        for(Permanent enchantment : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_ENCHANTMENT_PERMANENT, source.getControllerId(), source.getControllerId(), game)) {
            ContinuousEffect gainControl = new GainControlTargetEffect(Duration.EndOfGame);
            gainControl.setTargetPointer(new FixedTarget(enchantment.getId(), game));
            game.addEffect(gainControl, source);
            ret = true;
        }
        return ret;
    }
}

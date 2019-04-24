
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CastFromHandSourceCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.TapAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.watchers.common.CastFromHandWatcher;

/**
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class DreadCacodemon extends CardImpl {
    
    private static final FilterCreaturePermanent opponentsCreatures = new FilterCreaturePermanent("creatures your opponents control");
    static {
        opponentsCreatures.add(new ControllerPredicate(TargetController.OPPONENT));
    }
   
    private static final FilterCreaturePermanent otherCreaturesYouControl = new FilterCreaturePermanent("other creatures you control");
    static {
        otherCreaturesYouControl.add(new ControllerPredicate(TargetController.YOU));
        otherCreaturesYouControl.add(new AnotherPredicate());
    }
	
    public DreadCacodemon(UUID ownerId, CardSetInfo setInfo) {        
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{7}{B}{B}{B}");
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);
        
        // When Dread Cacodemon enters the battlefield, 
        // if you cast it from your hand, destroy all creatures your opponents control, then tap all other creatures you control. 
        TriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new DestroyAllEffect(opponentsCreatures, false));
        ability.addEffect(new TapAllEffect(otherCreaturesYouControl));
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, CastFromHandSourceCondition.instance,
                "When {this} enters the battlefield, if you cast it from your hand, destroy all creatures your opponents control, then tap all other creatures you control."), new CastFromHandWatcher());
    }

    public DreadCacodemon(final DreadCacodemon card) {
        super(card);
    }

    @Override
    public DreadCacodemon copy() {
        return new DreadCacodemon(this);
    }
}
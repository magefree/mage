package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CastFromHandSourcePermanentCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.TapAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.watchers.common.CastFromHandWatcher;

/**
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class DreadCacodemon extends CardImpl {

    private static final FilterCreaturePermanent otherCreaturesYouControl = new FilterCreaturePermanent("other creatures you control");
    static {
        otherCreaturesYouControl.add(TargetController.YOU.getControllerPredicate());
        otherCreaturesYouControl.add(AnotherPredicate.instance);
    }
	
    public DreadCacodemon(UUID ownerId, CardSetInfo setInfo) {        
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{7}{B}{B}{B}");
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // When Dread Cacodemon enters the battlefield, 
        // if you cast it from your hand, destroy all creatures your opponents control, then tap all other creatures you control. 
        TriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new DestroyAllEffect(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES));
        ability.addEffect(new TapAllEffect(otherCreaturesYouControl));
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, CastFromHandSourcePermanentCondition.instance,
                "When {this} enters the battlefield, if you cast it from your hand, destroy all creatures your opponents control, then tap all other creatures you control."), new CastFromHandWatcher());
    }

    private DreadCacodemon(final DreadCacodemon card) {
        super(card);
    }

    @Override
    public DreadCacodemon copy() {
        return new DreadCacodemon(this);
    }
}

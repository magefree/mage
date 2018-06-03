
package mage.cards.a;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.TargetPermanentPowerCount;
import mage.abilities.effects.common.DamageTargetControllerEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author FenrisulfrX
 */
public final class AgonizingDemise extends CardImpl {
    
    private static final FilterCreaturePermanent filterNonBlackCreature = new FilterCreaturePermanent("nonblack creature");
    static {
        filterNonBlackCreature.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }

    public AgonizingDemise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{B}");

        // Kicker {1}{R}
        this.addAbility(new KickerAbility("{1}{R}"));
        
        // Destroy target nonblack creature. It can't be regenerated.
        this.getSpellAbility().addEffect(new DestroyTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filterNonBlackCreature));
        
        //If Agonizing Demise was kicked, it deals damage equal to that creature's power to the creature's controller.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageTargetControllerEffect(new TargetPermanentPowerCount()),
                KickedCondition.instance,
                "if this spell was kicked, it deals damage equal to that creature's power to the creature's controller."));
        
    }

    public AgonizingDemise(final AgonizingDemise card) {
        super(card);
    }

    @Override
    public AgonizingDemise copy() {
        return new AgonizingDemise(this);
    }
}

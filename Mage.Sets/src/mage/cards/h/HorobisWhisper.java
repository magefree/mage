
package mage.cards.h;

import mage.ObjectColor;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.SpliceOntoArcaneAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class HorobisWhisper extends CardImpl {

    private static final FilterCreaturePermanent filterTarget = new FilterCreaturePermanent("nonblack creature");
    private static final FilterLandPermanent filterCondition = new FilterLandPermanent("Swamp");

    static {
        filterTarget.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
        filterCondition.add(new SubtypePredicate(SubType.SWAMP));
    }

    public HorobisWhisper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{B}{B}");
        this.subtype.add(SubType.ARCANE);



        // If you control a Swamp, destroy target nonblack creature.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new DestroyTargetEffect(), 
                new PermanentsOnTheBattlefieldCondition(filterCondition),"If you control a Swamp, destroy target nonblack creature"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filterTarget) );

        // Splice onto Arcane-Exile four cards from your graveyard.
        this.addAbility(new SpliceOntoArcaneAbility(new ExileFromGraveCost(new TargetCardInYourGraveyard(4,4, new FilterCard("cards")))));
        

    }

    public HorobisWhisper(final HorobisWhisper card) {
        super(card);
    }

    @Override
    public HorobisWhisper copy() {
        return new HorobisWhisper(this);
    }
}

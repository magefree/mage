
package mage.cards.v;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.ReturnToHandSpellEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class ViewFromAbove extends CardImpl {
    
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("white permanent you control");
    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public ViewFromAbove(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");


        // Target creature gains flying until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // If you control a white permanent, return View from Above to its owner's hand.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                ReturnToHandSpellEffect.getInstance(), 
                new PermanentsOnTheBattlefieldCondition(filter), 
                "If you control a white permanent, return {this} to its owner's hand"));
    }

    private ViewFromAbove(final ViewFromAbove card) {
        super(card);
    }

    @Override
    public ViewFromAbove copy() {
        return new ViewFromAbove(this);
    }
}

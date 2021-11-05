
package mage.cards.s;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public final class StatuteOfDenial extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("blue creature");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public StatuteOfDenial(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}{U}");


        // Counter target spell.  If you control a blue creature, draw a card, then discard a card.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DrawDiscardControllerEffect(1,1),
                new PermanentsOnTheBattlefieldCondition(filter),
                "If you control a blue creature, draw a card, then discard a card"));
    }

    private StatuteOfDenial(final StatuteOfDenial card) {
        super(card);
    }

    @Override
    public StatuteOfDenial copy() {
        return new StatuteOfDenial(this);
    }
}

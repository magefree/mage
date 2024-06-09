
package mage.cards.s;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author dustinconrad
 */
public final class SoulSpike extends CardImpl {

    private static final FilterCard filter = new FilterCard("black cards");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public SoulSpike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{5}{B}{B}");


        // You may exile two black cards from your hand rather than pay Soul Spike's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ExileFromHandCost(new TargetCardInHand(2, filter))));
        // Soul Spike deals 4 damage to any target and you gain 4 life.
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addEffect(new GainLifeEffect(4).concatBy("and"));
    }

    private SoulSpike(final SoulSpike card) {
        super(card);
    }

    @Override
    public SoulSpike copy() {
        return new SoulSpike(this);
    }
}

package mage.cards.s;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.dynamicvalue.common.TargetManaValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author L_J
 */
public final class SurgeOfStrength extends CardImpl {

    private static final FilterCard filter = new FilterCard("a red or green card");
    static{
        filter.add(Predicates.or(new ColorPredicate(ObjectColor.RED), new ColorPredicate(ObjectColor.GREEN)));
    }

    public SurgeOfStrength(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}{G}");

        // As an additional cost to cast Surge of Strength, discard a red or green card.
        this.getSpellAbility().addCost(new DiscardTargetCost(new TargetCardInHand(filter)));
        
        // Target creature gains trample and gets +X/+0 until end of turn, where X is that creature's converted mana cost.
        Effect effect = new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("Target creature gains trample");
        this.getSpellAbility().addEffect(effect);
        effect = new BoostTargetEffect(TargetManaValue.instance, StaticValue.get(0), Duration.EndOfTurn);
        effect.setText("and gets +X/+0 until end of turn, where X is that creature's mana value");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private SurgeOfStrength(final SurgeOfStrength card) {
        super(card);
    }

    @Override
    public SurgeOfStrength copy() {
        return new SurgeOfStrength(this);
    }
}

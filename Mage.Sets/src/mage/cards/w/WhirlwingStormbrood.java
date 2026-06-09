package mage.cards.w;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.cards.OmenCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author Jmlundeen
 */
public final class WhirlwingStormbrood extends OmenCard {

    private static final FilterCard filter = new FilterCard("sorcery spells and Dragon spells");

    static {
        filter.add(Predicates.or(
                CardType.SORCERY.getPredicate(),
                SubType.DRAGON.getPredicate()
        ));
    }

    public WhirlwingStormbrood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.DRAGON}, "{4}{U}",
                "Dynamic Soar",
                new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Whirlwing Stormbrood
        this.getLeftHalfCard().setPT(4, 3);

        // Flash
        this.getLeftHalfCard().addAbility(FlashAbility.getInstance());

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // You may cast sorcery spells and Dragon spells as though they had flash.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(new CastAsThoughItHadFlashAllEffect(Duration.WhileOnBattlefield, filter)));

        // Dynamic Soar
        // Put three +1/+1 counters on target creature you control.
        this.getRightHalfCard().getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance(3), StaticValue.get(3)));
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

        finalizeCard();
    }

    private WhirlwingStormbrood(final WhirlwingStormbrood card) {
        super(card);
    }

    @Override
    public WhirlwingStormbrood copy() {
        return new WhirlwingStormbrood(this);
    }
}

package mage.cards.h;

import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.AftermathAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class HeavenEarth extends SplitCard {

    private static final FilterCreaturePermanent filterWithouFlying = new FilterCreaturePermanent("creature without flying");

    static {
        filterWithouFlying.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public HeavenEarth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, new CardType[]{CardType.SORCERY}, "{X}{G}", "{X}{R}{R}", SpellAbilityType.SPLIT_AFTERMATH);

        // Falling
        // Falling deals X damage to each creature with flying.
        getLeftHalfCard().getSpellAbility().addEffect(new DamageAllEffect(GetXValue.instance, StaticFilters.FILTER_CREATURE_FLYING));

        // to
        // Earth
        // Earth deals X damage to each creature without flying.
        getRightHalfCard().addAbility(new AftermathAbility().setRuleAtTheTop(true));
        getRightHalfCard().getSpellAbility().addEffect(new DamageAllEffect(GetXValue.instance, filterWithouFlying));
    }

    private HeavenEarth(final HeavenEarth card) {
        super(card);
    }

    @Override
    public HeavenEarth copy() {
        return new HeavenEarth(this);
    }
}

package mage.cards.h;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.AftermathAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author Styxo
 */
public final class HeavenEarth extends SplitCard {

    private static final FilterCreaturePermanent filterFlying = new FilterCreaturePermanent("creature with flying");
    private static final FilterCreaturePermanent filterWithouFlying = new FilterCreaturePermanent("creature without flying");

    static {
        filterFlying.add(new AbilityPredicate(FlyingAbility.class));
        filterWithouFlying.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public HeavenEarth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, new CardType[]{CardType.SORCERY}, "{X}{G}", "{X}{R}{R}", SpellAbilityType.SPLIT_AFTERMATH);

        // Falling
        // Falling deals X damage to each creature with flying.
        getLeftHalfCard().getSpellAbility().addEffect(new DamageAllEffect(ManacostVariableValue.instance, filterFlying));

        // to
        // Earth
        // Earth deals X damage to each creature without flying.
        getRightHalfCard().addAbility(new AftermathAbility().setRuleAtTheTop(true));
        getRightHalfCard().getSpellAbility().addEffect(new DamageAllEffect(ManacostVariableValue.instance, filterWithouFlying));
    }

    public HeavenEarth(final HeavenEarth card) {
        super(card);
    }

    @Override
    public HeavenEarth copy() {
        return new HeavenEarth(this);
    }
}

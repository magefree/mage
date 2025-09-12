
package mage.cards.r;

import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageAllEffect;
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
 * @author LevelX2
 */
public final class RoughTumble extends SplitCard {

    private static final FilterCreaturePermanent filterWithoutFlying = new FilterCreaturePermanent("creature without flying");

    static {
        filterWithoutFlying.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public RoughTumble(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}", "{5}{R}", SpellAbilityType.SPLIT);

        // Rough
        // Rough deals 2 damage to each creature without flying.
        Effect effect = new DamageAllEffect(2, filterWithoutFlying);
        effect.setText("{this} deals 2 damage to each creature without flying");
        getLeftHalfCard().getSpellAbility().addEffect(effect);

        // Tumble
        // Tumble deals 6 damage to each creature with flying.
        effect = new DamageAllEffect(6, StaticFilters.FILTER_CREATURE_FLYING);
        effect.setText("{this} deals 6 damage to each creature with flying");
        getRightHalfCard().getSpellAbility().addEffect(effect);

    }

    private RoughTumble(final RoughTumble card) {
        super(card);
    }

    @Override
    public RoughTumble copy() {
        return new RoughTumble(this);
    }
}

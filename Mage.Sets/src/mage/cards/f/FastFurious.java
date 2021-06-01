package mage.cards.f;

import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FastFurious extends SplitCard {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature without flying");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public FastFurious(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.INSTANT}, new CardType[]{CardType.SORCERY},
                "{2}{R}", "{3}{R}{R}", SpellAbilityType.SPLIT
        );

        // Fast
        // Discard a card, then draw two cards.
        this.getLeftHalfCard().getSpellAbility().addEffect(new DiscardControllerEffect(1));
        this.getLeftHalfCard().getSpellAbility().addEffect(
                new DrawCardSourceControllerEffect(2).concatBy(", then")
        );

        // Furious
        // Furious deals 3 damage to each creature without flying.
        this.getRightHalfCard().getSpellAbility().addEffect(new DamageAllEffect(3, filter));
    }

    private FastFurious(final FastFurious card) {
        super(card);
    }

    @Override
    public FastFurious copy() {
        return new FastFurious(this);
    }
}

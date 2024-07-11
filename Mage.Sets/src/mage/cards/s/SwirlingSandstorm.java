package mage.cards.s;

import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author Quercitron
 */
public final class SwirlingSandstorm extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature without flying");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public SwirlingSandstorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Threshold - Swirling Sandstorm deals 5 damage to each creature without flying if seven or more cards are in your graveyard.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageAllEffect(5, filter), ThresholdCondition.instance,
                AbilityWord.THRESHOLD.formatWord() + "{this} deals " +
                "5 damage to each creature without flying if seven or more cards are in your graveyard."
        ));
    }

    private SwirlingSandstorm(final SwirlingSandstorm card) {
        super(card);
    }

    @Override
    public SwirlingSandstorm copy() {
        return new SwirlingSandstorm(this);
    }
}

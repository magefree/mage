
package mage.cards.s;

import java.util.UUID;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author Quercitron
 */
public final class SwirlingSandstorm extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature without flying");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public SwirlingSandstorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{R}");

        // Threshold - Swirling Sandstorm deals 5 damage to each creature without flying if seven or more cards are in your graveyard.
        Effect effect = new ConditionalOneShotEffect(
                new DamageAllEffect(5, filter),
                new CardsInControllerGraveyardCondition(7),
                "<i>Threshold</i> &mdash; {this} deals 5 damage to each creature without flying if seven or more cards are in your graveyard.");
        this.getSpellAbility().addEffect(effect);
    }

    private SwirlingSandstorm(final SwirlingSandstorm card) {
        super(card);
    }

    @Override
    public SwirlingSandstorm copy() {
        return new SwirlingSandstorm(this);
    }
}

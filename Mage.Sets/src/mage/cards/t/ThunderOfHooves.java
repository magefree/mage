
package mage.cards.t;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author fireshoes
 */
public final class ThunderOfHooves extends CardImpl {
    
    private static final FilterCreaturePermanent filterNotFlying = new FilterCreaturePermanent();
    private static final FilterPermanent filterBeasts = new FilterPermanent();

    static {
        filterNotFlying.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
        filterBeasts.add(SubType.BEAST.getPredicate());
    }

    public ThunderOfHooves(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{R}");

        // Thunder of Hooves deals X damage to each creature without flying and each player, where X is the number of Beasts on the battlefield.
        Effect effect = new DamageEverythingEffect(new PermanentsOnBattlefieldCount(new FilterPermanent(filterBeasts)), new FilterCreaturePermanent(filterNotFlying));
        effect.setText("{this} deals X damage to each creature without flying and each player, where X is the number of Beasts on the battlefield");
        this.getSpellAbility().addEffect(effect);
    }

    private ThunderOfHooves(final ThunderOfHooves card) {
        super(card);
    }

    @Override
    public ThunderOfHooves copy() {
        return new ThunderOfHooves(this);
    }
}


package mage.cards.r;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class RockslideAmbush extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Mountain you control");

    static {
        filter.add(new SubtypePredicate(SubType.MOUNTAIN));
    }

    public RockslideAmbush(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{R}");

        // Rockslide Ambush deals damage to target creature equal to the number of Mountains you control.
        this.getSpellAbility().addEffect(new DamageTargetEffect(new PermanentsOnBattlefieldCount(filter)));                                                                                                     this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public RockslideAmbush(final RockslideAmbush card) {
        super(card);
    }

    @Override
    public RockslideAmbush copy() {
        return new RockslideAmbush(this);
    }
}


package mage.cards.m;

import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.DamageTargetControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 *
 * @author North
 */
public final class MagneticMine extends CardImpl {
    private static final FilterPermanent filter = new FilterArtifactPermanent("another artifact");

    static {
        filter.add(AnotherPredicate.instance);
    }
    public MagneticMine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");
        // Whenever another artifact is put into a graveyard from the battlefield, Magnetic Mine deals 2 damage to that artifact’s controller.
        this.addAbility(new PutIntoGraveFromBattlefieldAllTriggeredAbility(
                new DamageTargetControllerEffect(2, "artifact"), false, filter, true));
    }

    private MagneticMine(final MagneticMine card) {
        super(card);
    }

    @Override
    public MagneticMine copy() {
        return new MagneticMine(this);
    }
}

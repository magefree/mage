
package mage.cards.t;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class TendrilsOfCorruption extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Swamp you control");

    static {
        filter.add(new SubtypePredicate(SubType.SWAMP));
    }

    public TendrilsOfCorruption(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{B}");

        this.getSpellAbility().addEffect(new DamageTargetEffect(new PermanentsOnBattlefieldCount(filter)));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new GainLifeEffect(new PermanentsOnBattlefieldCount(filter)));
    }

    public TendrilsOfCorruption(final TendrilsOfCorruption card) {
        super(card);
    }

    @Override
    public TendrilsOfCorruption copy() {
        return new TendrilsOfCorruption(this);
    }
}

package mage.cards.d;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.MorphSpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author North
 */
public final class DreamChisel extends CardImpl {
    public DreamChisel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Face-down creature spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MorphSpellsCostReductionControllerEffect(1)));
    }

    private DreamChisel(final DreamChisel card) {
        super(card);
    }

    @Override
    public DreamChisel copy() {
        return new DreamChisel(this);
    }
}
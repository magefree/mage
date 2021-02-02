package mage.cards.h;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.SpellsCostReductionAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class HelmOfAwakening extends CardImpl {

    public HelmOfAwakening(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Spells cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionAllEffect(1)));
    }

    private HelmOfAwakening(final HelmOfAwakening card) {
        super(card);
    }

    @Override
    public HelmOfAwakening copy() {
        return new HelmOfAwakening(this);
    }
}

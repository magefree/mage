package mage.cards.t;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.TapAllTargetPlayerControlsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author LoneFox

 */
public final class TectonicInstability extends CardImpl {

    public TectonicInstability(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{R}");

        // Whenever a land enters the battlefield, tap all lands its controller controls.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD,
                new TapAllTargetPlayerControlsEffect(StaticFilters.FILTER_LANDS).setText("tap all lands its controller controls"),
                StaticFilters.FILTER_LAND, false, SetTargetPointer.PLAYER));
    }

    private TectonicInstability(final TectonicInstability card) {
        super(card);
    }

    @Override
    public TectonicInstability copy() {
        return new TectonicInstability(this);
    }
}

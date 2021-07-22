
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author Styxo
 */
public final class AudaciousInfiltrator extends CardImpl {

    public AudaciousInfiltrator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Audacious Infiltrator can't be blocked by artifact creatures.
        Effect effect = new CantBeBlockedByCreaturesSourceEffect(StaticFilters.FILTER_PERMANENT_ARTIFACT_CREATURE, Duration.WhileOnBattlefield);
        effect.setText("{this} can't be blocked by artifact creatures");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private AudaciousInfiltrator(final AudaciousInfiltrator card) {
        super(card);
    }

    @Override
    public AudaciousInfiltrator copy() {
        return new AudaciousInfiltrator(this);
    }
}

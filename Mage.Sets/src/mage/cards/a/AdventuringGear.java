

package mage.cards.a;

import java.util.UUID;
import mage.abilities.common.LandfallAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class AdventuringGear extends CardImpl {

    public AdventuringGear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");
        this.subtype.add(SubType.EQUIPMENT);
        
        // Landfall — Whenever a land enters the battlefield under your control, equipped creature gets +2/+2 until end of turn.
        this.addAbility(new LandfallAbility(new BoostEquippedEffect(2, 2, Duration.EndOfTurn), false));
        
        // Equip {1} ({1}: Attach to target creature you control. Equip only as a sorcery.)
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(1)));
    }

    private AdventuringGear(final AdventuringGear card) {
        super(card);
    }

    @Override
    public AdventuringGear copy() {
        return new AdventuringGear(this);
    }
}
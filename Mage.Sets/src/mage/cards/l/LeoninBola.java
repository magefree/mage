package mage.cards.l;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.UnattachCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityWithAttachmentEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class LeoninBola extends CardImpl {

    public LeoninBola(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has "{tap}, Unattach Leonin Bola: Tap target creature."
        this.addAbility(new SimpleStaticAbility(new GainAbilityWithAttachmentEffect(
                "equipped creature has \"{T}, Unattach {this}: Tap target creature.\"",
                new TapTargetEffect(), new TargetCreaturePermanent(), new UnattachCost(), new TapSourceCost()
        )));

        // Equip {1}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(1)));
    }

    private LeoninBola(final LeoninBola card) {
        super(card);
    }

    @Override
    public LeoninBola copy() {
        return new LeoninBola(this);
    }
}

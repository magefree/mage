package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScreamingShield extends CardImpl {

    public ScreamingShield(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +0/+3 and has "{2}, {T}: Target player puts the top three cards of their library into their graveyard."
        Ability toAdd = new SimpleActivatedAbility(
                new MillCardsTargetEffect(3), new GenericManaCost(2)
        );
        toAdd.addCost(new TapSourceCost());
        toAdd.addTarget(new TargetPlayer());
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(0, 3));
        ability.addEffect(new GainAbilityAttachedEffect(
                toAdd, AttachmentType.EQUIPMENT
        ).setText("and has \"{2}, {T}: Target player mills three cards.\""));
        this.addAbility(ability);

        // Equip {3}
        this.addAbility(new EquipAbility(3));
    }

    private ScreamingShield(final ScreamingShield card) {
        super(card);
    }

    @Override
    public ScreamingShield copy() {
        return new ScreamingShield(this);
    }
}
// AAAAAAAAAAAAAAAAAAAAAAHHHHHHHHHHHHHHHHHHHHH

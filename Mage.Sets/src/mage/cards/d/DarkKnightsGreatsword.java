package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.continuous.AddCardSubtypeAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.JobSelectAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DarkKnightsGreatsword extends CardImpl {

    public DarkKnightsGreatsword(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{B}");

        this.subtype.add(SubType.EQUIPMENT);

        // Job select
        this.addAbility(new JobSelectAbility());

        // Equipped creature gets +3/+0 and is a Knight in addition to its other types.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(3, 0));
        ability.addEffect(new AddCardSubtypeAttachedEffect(
                SubType.KNIGHT, AttachmentType.EQUIPMENT
        ).setText("and is a Knight in addition to its other types"));
        this.addAbility(ability);

        // Chaosbringer -- Equip--Pay 3 life. Activate only once each turn.
        EquipAbility equipAbility = new EquipAbility(Outcome.BoostCreature, new PayLifeCost(3));
        equipAbility.setMaxActivationsPerTurn(1);
        this.addAbility(equipAbility.withFlavorWord("Chaosbringer"));
    }

    private DarkKnightsGreatsword(final DarkKnightsGreatsword card) {
        super(card);
    }

    @Override
    public DarkKnightsGreatsword copy() {
        return new DarkKnightsGreatsword(this);
    }
}

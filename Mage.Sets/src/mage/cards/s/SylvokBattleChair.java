package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.ForMirrodinAbility;
import mage.abilities.keyword.TrampleAbility;
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
public final class SylvokBattleChair extends CardImpl {

    public SylvokBattleChair(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}{G}{G}");

        this.subtype.add(SubType.EQUIPMENT);

        // For Mirrodin!
        this.addAbility(new ForMirrodinAbility());

        // Equipped creature gets +4/+4 and has trample.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(4, 4));
        ability.addEffect(new GainAbilityAttachedEffect(
                TrampleAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has trample"));
        this.addAbility(ability);

        // Equip {5}{G}{G}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new ManaCostsImpl<>("{5}{G}{G}")));
    }

    private SylvokBattleChair(final SylvokBattleChair card) {
        super(card);
    }

    @Override
    public SylvokBattleChair copy() {
        return new SylvokBattleChair(this);
    }
}

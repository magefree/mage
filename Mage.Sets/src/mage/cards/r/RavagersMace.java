package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAttachToTarget;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.PartyCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.hint.common.PartyCountHint;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.MenaceAbility;
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
public final class RavagersMace extends CardImpl {

    public RavagersMace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{B}{R}");

        this.subtype.add(SubType.EQUIPMENT);

        // When Ravager's Mace enters the battlefield, attach it to target creature you control.
        this.addAbility(new EntersBattlefieldAttachToTarget());

        // Equipped creature gets +1/+0 for each creature in your party and has menace.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(
                PartyCount.instance, StaticValue.get(0)
        ).setText("equipped creature gets +1/+0 for each creature in your party"));
        ability.addEffect(new GainAbilityAttachedEffect(
                new MenaceAbility(), AttachmentType.EQUIPMENT
        ).setText("and has menace. " + PartyCount.getReminder()));
        this.addAbility(ability.addHint(PartyCountHint.instance));

        // Equip {2}{B}{R}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new ManaCostsImpl<>("{2}{B}{R}"), false));
    }

    private RavagersMace(final RavagersMace card) {
        super(card);
    }

    @Override
    public RavagersMace copy() {
        return new RavagersMace(this);
    }
}

package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.abilities.effects.keyword.ExploreSourceEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class GlowcapLantern extends CardImpl {

    public GlowcapLantern(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{G}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has "You may look at the top card of your library any time" and "Whenever this creature attacks, it explores."
        Ability ability = new SimpleStaticAbility(new GainAbilityAttachedEffect(
                new SimpleStaticAbility(new LookAtTopCardOfLibraryAnyTimeEffect()),
                AttachmentType.EQUIPMENT
        ).setText("Equipped creature has \"You may look at the top card of your library any time\""));
        ability.addEffect(new GainAbilityAttachedEffect(
                new AttacksTriggeredAbility(new ExploreSourceEffect(false, "{this}")),
                AttachmentType.EQUIPMENT
        ).setText("and \"Whenever this creature attacks, it explores.\""));
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(2), false));
    }

    private GlowcapLantern(final GlowcapLantern card) {
        super(card);
    }

    @Override
    public GlowcapLantern copy() {
        return new GlowcapLantern(this);
    }
}

package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PirateHat extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent(SubType.PIRATE, "Pirate");

    public PirateHat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{U}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1 and has "Whenever this creature attacks, draw a card, then discard a card."
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(1, 1));
        ability.addEffect(new GainAbilityAttachedEffect(
                new AttacksTriggeredAbility(
                        new DrawDiscardControllerEffect(1, 1)
                ).setTriggerPhrase("Whenever this creature attacks, "), AttachmentType.EQUIPMENT
        ).setText("and has \"Whenever this creature attacks, draw a card, then discard a card.\""));
        this.addAbility(ability);

        // Equip Pirate {1}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(1), new TargetPermanent(filter), false));

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private PirateHat(final PirateHat card) {
        super(card);
    }

    @Override
    public PirateHat copy() {
        return new PirateHat(this);
    }
}

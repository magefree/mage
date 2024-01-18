package mage.cards.p;

import java.util.UUID;

import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.keyword.CraftAbility;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * Paleontologist's Pick-Axe {2}
 * Artifact - Equipment
 * Whenever equipped creature attacks, draw a card, then discard a card.
 * Equip {1}
 * Craft with one or more creatures {5}
 *
 * @author DominionSpy
 */
public class PaleontologistsPickAxe extends CardImpl {

    public PaleontologistsPickAxe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.secondSideCardClazz = mage.cards.d.DinosaurHeaddress.class;

        this.subtype.add(SubType.EQUIPMENT);

        // Whenever equipped creature attacks, draw a card, then discard a card.
        this.addAbility(new AttacksAttachedTriggeredAbility(new DrawDiscardControllerEffect()));

        // Equip {1}
        this.addAbility(new EquipAbility(1));

        // Craft with one or more creatures {5}
        this.addAbility(new CraftAbility(
                "{5}", "one or more creatures", "other creatures you control and/or" +
                "creature cards in your graveyard", 1, Integer.MAX_VALUE, CardType.CREATURE.getPredicate()
        ));
    }

    protected PaleontologistsPickAxe(final PaleontologistsPickAxe card) {
        super(card);
    }

    @Override
    public PaleontologistsPickAxe copy() {
        return new PaleontologistsPickAxe(this);
    }
}

package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.effects.keyword.AmassEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author muz
 */
public final class MistyMountainsRaider extends CardImpl {

    public MistyMountainsRaider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever you attack, amass Goblins 2.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
            new AmassEffect(2, SubType.GOBLIN), 1
        ));
    }

    private MistyMountainsRaider(final MistyMountainsRaider card) {
        super(card);
    }

    @Override
    public MistyMountainsRaider copy() {
        return new MistyMountainsRaider(this);
    }
}

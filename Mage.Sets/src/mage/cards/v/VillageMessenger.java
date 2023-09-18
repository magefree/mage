package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class VillageMessenger extends CardImpl {

    public VillageMessenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.secondSideCardClazz = mage.cards.m.MoonriseIntruder.class;

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // At the beginning of each upkeep, if no spells were cast last turn, transform Village Messenger.
        this.addAbility(new TransformAbility());
        this.addAbility(new WerewolfFrontTriggeredAbility());
    }

    private VillageMessenger(final VillageMessenger card) {
        super(card);
    }

    @Override
    public VillageMessenger copy() {
        return new VillageMessenger(this);
    }
}
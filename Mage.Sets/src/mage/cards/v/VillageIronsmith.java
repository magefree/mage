package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class VillageIronsmith extends CardImpl {

    public VillageIronsmith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WEREWOLF);

        this.secondSideCardClazz = mage.cards.i.Ironfang.class;

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(FirstStrikeAbility.getInstance());

        // At the beginning of each upkeep, if no spells were cast last turn, transform Village Ironsmith.
        this.addAbility(new TransformAbility());
        this.addAbility(new WerewolfFrontTriggeredAbility());
    }

    private VillageIronsmith(final VillageIronsmith card) {
        super(card);
    }

    @Override
    public VillageIronsmith copy() {
        return new VillageIronsmith(this);
    }
}

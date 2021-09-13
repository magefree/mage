package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author North
 */
public final class MondronenShaman extends CardImpl {

    public MondronenShaman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.subtype.add(SubType.WEREWOLF);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        this.secondSideCardClazz = mage.cards.t.TovolarsMagehunter.class;

        // At the beginning of each upkeep, if no spells were cast last turn, transform Mondronen Shaman.
        this.addAbility(new TransformAbility());
        this.addAbility(new WerewolfFrontTriggeredAbility());
    }

    private MondronenShaman(final MondronenShaman card) {
        super(card);
    }

    @Override
    public MondronenShaman copy() {
        return new MondronenShaman(this);
    }
}

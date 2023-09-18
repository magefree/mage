package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class HanweirWatchkeep extends CardImpl {

    public HanweirWatchkeep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.WEREWOLF);

        this.secondSideCardClazz = mage.cards.b.BaneOfHanweir.class;

        this.power = new MageInt(1);
        this.toughness = new MageInt(5);

        this.addAbility(DefenderAbility.getInstance());
        // At the beginning of each upkeep, if no spells were cast last turn, transform Hanweir Watchkeep.
        this.addAbility(new TransformAbility());
        this.addAbility(new WerewolfFrontTriggeredAbility());
    }

    private HanweirWatchkeep(final HanweirWatchkeep card) {
        super(card);
    }

    @Override
    public HanweirWatchkeep copy() {
        return new HanweirWatchkeep(this);
    }
}

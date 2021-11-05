package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class HinterlandLogger extends CardImpl {

    public HinterlandLogger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.secondSideCardClazz = mage.cards.t.TimberShredder.class;

        // At the beginning of each upkeep, if no spells were cast last turn, transform Hinterland Logger.
        this.addAbility(new TransformAbility());
        this.addAbility(new WerewolfFrontTriggeredAbility());
    }

    private HinterlandLogger(final HinterlandLogger card) {
        super(card);
    }

    @Override
    public HinterlandLogger copy() {
        return new HinterlandLogger(this);
    }
}

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
 * @author BetaSteward
 */
public final class HinterlandHermit extends CardImpl {

    public HinterlandHermit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WEREWOLF);

        this.secondSideCardClazz = mage.cards.h.HinterlandScourge.class;

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // At the beginning of each upkeep, if no spells were cast last turn, transform Hinterland Hermit.
        this.addAbility(new TransformAbility());
        this.addAbility(new WerewolfFrontTriggeredAbility());
    }

    private HinterlandHermit(final HinterlandHermit card) {
        super(card);
    }

    @Override
    public HinterlandHermit copy() {
        return new HinterlandHermit(this);
    }
}

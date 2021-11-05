package mage.cards.k;

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
 * @author North
 */
public final class KruinOutlaw extends CardImpl {

    public KruinOutlaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.subtype.add(SubType.WEREWOLF);

        this.secondSideCardClazz = mage.cards.t.TerrorOfKruinPass.class;

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(FirstStrikeAbility.getInstance());
        // At the beginning of each upkeep, if no spells were cast last turn, transform Kruin Outlaw.
        this.addAbility(new TransformAbility());
        this.addAbility(new WerewolfFrontTriggeredAbility());
    }

    private KruinOutlaw(final KruinOutlaw card) {
        super(card);
    }

    @Override
    public KruinOutlaw copy() {
        return new KruinOutlaw(this);
    }
}

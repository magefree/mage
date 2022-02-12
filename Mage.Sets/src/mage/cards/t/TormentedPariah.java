package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class TormentedPariah extends CardImpl {

    public TormentedPariah(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.WEREWOLF);

        this.secondSideCardClazz = mage.cards.r.RampagingWerewolf.class;

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // At the beginning of each upkeep, if no spells were cast last turn, transform Tormented Pariah.
        this.addAbility(new TransformAbility());
        this.addAbility(new WerewolfFrontTriggeredAbility());
    }

    private TormentedPariah(final TormentedPariah card) {
        super(card);
    }

    @Override
    public TormentedPariah copy() {
        return new TormentedPariah(this);
    }
}

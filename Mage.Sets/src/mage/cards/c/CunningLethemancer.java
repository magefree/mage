package mage.cards.c;

import mage.MageInt;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author North
 */
public final class CunningLethemancer extends CardImpl {

    public CunningLethemancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DiscardEachPlayerEffect()));
    }

    private CunningLethemancer(final CunningLethemancer card) {
        super(card);
    }

    @Override
    public CunningLethemancer copy() {
        return new CunningLethemancer(this);
    }
}

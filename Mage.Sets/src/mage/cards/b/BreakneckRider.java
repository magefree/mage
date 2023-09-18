package mage.cards.b;

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
public final class BreakneckRider extends CardImpl {

    public BreakneckRider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");
        this.subtype.add(SubType.HUMAN, SubType.SCOUT, SubType.WEREWOLF);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.secondSideCardClazz = mage.cards.n.NeckBreaker.class;

        // At the beginning of each upkeep, if no spells were cast last turn, transform Breakneck Rider.
        this.addAbility(new TransformAbility());
        this.addAbility(new WerewolfFrontTriggeredAbility());
    }

    private BreakneckRider(final BreakneckRider card) {
        super(card);
    }

    @Override
    public BreakneckRider copy() {
        return new BreakneckRider(this);
    }
}

package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class RankRat extends CardImpl {

    public RankRat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.RAT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When this creature enters, each opponent discards a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DiscardEachPlayerEffect(TargetController.OPPONENT)));
    }

    private RankRat(final RankRat card) {
        super(card);
    }

    @Override
    public RankRat copy() {
        return new RankRat(this);
    }
}

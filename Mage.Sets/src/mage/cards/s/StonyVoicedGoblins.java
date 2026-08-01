package mage.cards.s;

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
public final class StonyVoicedGoblins extends CardImpl {

    public StonyVoicedGoblins(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When this creature enters, each opponent discards a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DiscardEachPlayerEffect(TargetController.OPPONENT)));
    }

    private StonyVoicedGoblins(final StonyVoicedGoblins card) {
        super(card);
    }

    @Override
    public StonyVoicedGoblins copy() {
        return new StonyVoicedGoblins(this);
    }
}

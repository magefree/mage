package mage.cards.n;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NezumiInformant extends CardImpl {

    public NezumiInformant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Nezumi Informant enters the battlefield, each opponent discards a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DiscardEachPlayerEffect(TargetController.OPPONENT)));
    }

    private NezumiInformant(final NezumiInformant card) {
        super(card);
    }

    @Override
    public NezumiInformant copy() {
        return new NezumiInformant(this);
    }
}

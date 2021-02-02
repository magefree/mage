
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class MiasmicMummy extends CardImpl {

    public MiasmicMummy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.JACKAL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Miasmic Mummy enters the battlefield, each player discards a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DiscardEachPlayerEffect()));
    }

    private MiasmicMummy(final MiasmicMummy card) {
        super(card);
    }

    @Override
    public MiasmicMummy copy() {
        return new MiasmicMummy(this);
    }
}

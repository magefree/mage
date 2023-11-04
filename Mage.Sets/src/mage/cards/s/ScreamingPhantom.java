package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScreamingPhantom extends CardImpl {

    public ScreamingPhantom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Screaming Phantom attacks, mill a card.
        this.addAbility(new AttacksTriggeredAbility(new MillCardsControllerEffect(1)));
    }

    private ScreamingPhantom(final ScreamingPhantom card) {
        super(card);
    }

    @Override
    public ScreamingPhantom copy() {
        return new ScreamingPhantom(this);
    }
}

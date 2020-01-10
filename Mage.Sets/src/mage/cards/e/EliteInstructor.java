package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EliteInstructor extends CardImpl {

    public EliteInstructor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Elite Instructor enters the battlefield, draw a card, then discard a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawDiscardControllerEffect(1, 1)));
    }

    private EliteInstructor(final EliteInstructor card) {
        super(card);
    }

    @Override
    public EliteInstructor copy() {
        return new EliteInstructor(this);
    }
}

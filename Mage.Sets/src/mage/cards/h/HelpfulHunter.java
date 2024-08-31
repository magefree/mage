package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HelpfulHunter extends CardImpl {

    public HelpfulHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.CAT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When this creature enters, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)));
    }

    private HelpfulHunter(final HelpfulHunter card) {
        super(card);
    }

    @Override
    public HelpfulHunter copy() {
        return new HelpfulHunter(this);
    }
}

package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SphinxMindbreaker extends CardImpl {

    public SphinxMindbreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");

        this.subtype.add(SubType.SPHINX);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Sphinx Mindbreaker enters the battlefield, each opponent puts the top ten cards of their library into their graveyard.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new MillCardsEachPlayerEffect(10, TargetController.OPPONENT)
        ));
    }

    private SphinxMindbreaker(final SphinxMindbreaker card) {
        super(card);
    }

    @Override
    public SphinxMindbreaker copy() {
        return new SphinxMindbreaker(this);
    }
}

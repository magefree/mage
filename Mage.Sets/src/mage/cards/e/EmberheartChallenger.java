package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.ValiantTriggeredAbility;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EmberheartChallenger extends CardImpl {

    public EmberheartChallenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.MOUSE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Prowess
        this.addAbility(new ProwessAbility());

        // Valiant -- Whenever Emberheart Challenger becomes the target of a spell or ability you control for the first time each turn, exile the top card of your library. Until end of turn, you may play that card.
        this.addAbility(new ValiantTriggeredAbility(new ExileTopXMayPlayUntilEffect(1, Duration.EndOfTurn)));
    }

    private EmberheartChallenger(final EmberheartChallenger card) {
        super(card);
    }

    @Override
    public EmberheartChallenger copy() {
        return new EmberheartChallenger(this);
    }
}

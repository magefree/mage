package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.common.ShuffleIntoLibrarySourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class BlitzHellion extends CardImpl {

    public BlitzHellion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{G}");
        this.subtype.add(SubType.HELLION);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // At the beginning of the end step, Blitz Hellion's owner shuffles it into their library.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new ShuffleIntoLibrarySourceEffect()
                        .setText("{this}'s owner shuffles it into their library."),
                TargetController.NEXT, false
        ));
    }

    private BlitzHellion(final BlitzHellion card) {
        super(card);
    }

    @Override
    public BlitzHellion copy() {
        return new BlitzHellion(this);
    }
}


package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.game.permanent.token.TreasureToken;

/**
 *
 * @author TheElk801
 */
public final class CaptainLanneryStorm extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("a Treasure");

    static {
        filter.add(SubType.TREASURE.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public CaptainLanneryStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Captain lannery Storm attacks, create a colorless Treasure artifact token with "{T}, Sacrifice this artifact: Add one mana of any color."
        this.addAbility(new AttacksTriggeredAbility(new CreateTokenEffect(new TreasureToken()), false));

        // Whenever you sacrifice a Treasure, Captain Lannery Storm gets +1/+0 until end of turn.
        this.addAbility(new SacrificePermanentTriggeredAbility(new BoostSourceEffect(1, 0, Duration.EndOfTurn), filter));
    }

    private CaptainLanneryStorm(final CaptainLanneryStorm card) {
        super(card);
    }

    @Override
    public CaptainLanneryStorm copy() {
        return new CaptainLanneryStorm(this);
    }
}

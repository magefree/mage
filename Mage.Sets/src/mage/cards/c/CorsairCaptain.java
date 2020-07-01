package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CorsairCaptain extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.PIRATE, "Pirates");

    public CorsairCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Corsair Captain enters the battlefield, create a treasure token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new TreasureToken())));

        // Other Pirates you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(
                new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter, true)
        ));
    }

    private CorsairCaptain(final CorsairCaptain card) {
        super(card);
    }

    @Override
    public CorsairCaptain copy() {
        return new CorsairCaptain(this);
    }
}

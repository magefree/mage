package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.MapToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WaterwindScout extends CardImpl {

    public WaterwindScout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Waterwind Scout enters the battlefield, create a Map token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new MapToken())));
    }

    private WaterwindScout(final WaterwindScout card) {
        super(card);
    }

    @Override
    public WaterwindScout copy() {
        return new WaterwindScout(this);
    }
}

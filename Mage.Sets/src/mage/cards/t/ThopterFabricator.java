package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.ThopterColorlessToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThopterFabricator extends CardImpl {

    public ThopterFabricator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{U}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you draw your second card each turn, create a 1/1 colorless Thopter artifact creature token with flying.
        this.addAbility(new DrawNthCardTriggeredAbility(new CreateTokenEffect(new ThopterColorlessToken())));

        // Crew 2
        this.addAbility(new CrewAbility(2));
    }

    private ThopterFabricator(final ThopterFabricator card) {
        super(card);
    }

    @Override
    public ThopterFabricator copy() {
        return new ThopterFabricator(this);
    }
}

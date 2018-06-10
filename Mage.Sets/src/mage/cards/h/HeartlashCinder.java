
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ChromaOutrageShamanCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class HeartlashCinder extends CardImpl {

    public HeartlashCinder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Chroma - When Heartlash Cinder enters the battlefield, it gets +X/+0 until end of turn, where X is the number of red mana symbols in the mana costs of permanents you control.
        ContinuousEffect effect = new BoostSourceEffect(new ChromaHeartlashCinderCount(), new StaticValue(0), Duration.EndOfTurn, true);
        effect.setText("<i>Chroma</i> &mdash; When Heartlash Cinder enters the battlefield, it gets +X/+0 until end of turn, where X is the number of red mana symbols in the mana costs of permanents you control.");
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect, false, true));

    }

    public HeartlashCinder(final HeartlashCinder card) {
        super(card);
    }

    @Override
    public HeartlashCinder copy() {
        return new HeartlashCinder(this);
    }
}

class ChromaHeartlashCinderCount implements DynamicValue {

    private int chroma;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        chroma = 0;
        for (Card card : game.getBattlefield().getAllActivePermanents(new FilterControlledPermanent(), sourceAbility.getControllerId(), game)) {
            chroma += card.getManaCost().getMana().getRed();
        }
        return chroma;
    }

    @Override
    public DynamicValue copy() {
        return new ChromaOutrageShamanCount();
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "";
    }
}

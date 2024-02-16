package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.EscapesWithAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.EscapeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PhoenixOfAsh extends CardImpl {

    public PhoenixOfAsh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");

        this.subtype.add(SubType.PHOENIX);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // {2}{R}: Phoenix of Ash gets +2/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new BoostSourceEffect(2, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{2}{R}")
        ));

        // Escape — {2}{R}{R}, Exile three other cards from your graveyard.
        this.addAbility(new EscapeAbility(this, "{2}{R}{R}", 3));

        // Phoenix of Ash escapes with a +1/+1 counter on it.
        this.addAbility(new EscapesWithAbility(1));
    }

    private PhoenixOfAsh(final PhoenixOfAsh card) {
        super(card);
    }

    @Override
    public PhoenixOfAsh copy() {
        return new PhoenixOfAsh(this);
    }
}

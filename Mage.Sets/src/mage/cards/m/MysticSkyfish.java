package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.DrawCardTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MysticSkyfish extends CardImpl {

    public MysticSkyfish(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.FISH);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Whenever you draw your second card each turn, Mystic Skyfish gains flying until end of turn.
        this.addAbility(new DrawCardTriggeredAbility(
                new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn), false, 2)
        );
    }

    private MysticSkyfish(final MysticSkyfish card) {
        super(card);
    }

    @Override
    public MysticSkyfish copy() {
        return new MysticSkyfish(this);
    }
}

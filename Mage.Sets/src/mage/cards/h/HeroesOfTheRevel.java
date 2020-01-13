package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.HeroicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.permanent.token.SatyrCantBlockToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HeroesOfTheRevel extends CardImpl {

    public HeroesOfTheRevel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.SATYR);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Heroes of the Revel enters the battlefield, create a 1/1 red Satyr creature token with "This creature can't block."
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new SatyrCantBlockToken())));

        // Whenever you cast a spell that targets Heroes of the Revel, creatures you control get +1/+0 until end of turn.
        this.addAbility(new HeroicAbility(
                new BoostControlledEffect(1, 0, Duration.EndOfTurn), false, false
        ));
    }

    private HeroesOfTheRevel(final HeroesOfTheRevel card) {
        super(card);
    }

    @Override
    public HeroesOfTheRevel copy() {
        return new HeroesOfTheRevel(this);
    }
}

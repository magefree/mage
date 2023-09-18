package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.RiotAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FrenziedArynx extends CardImpl {

    public FrenziedArynx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Riot
        this.addAbility(new RiotAbility());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // {4}{R}{G}: Frenzied Arynx gets +3/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new BoostSourceEffect(3, 0, Duration.EndOfTurn),
                new ManaCostsImpl<>("{4}{R}{G}")
        ));
    }

    private FrenziedArynx(final FrenziedArynx card) {
        super(card);
    }

    @Override
    public FrenziedArynx copy() {
        return new FrenziedArynx(this);
    }
}

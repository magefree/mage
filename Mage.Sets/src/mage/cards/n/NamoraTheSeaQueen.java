package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.game.permanent.token.MerfolkToken;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.PowerUpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class NamoraTheSeaQueen extends CardImpl {

    public NamoraTheSeaQueen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Power-up -- {5}{U}: Put a +1/+1 counter on Namora. Create two 1/1 blue Merfolk creature tokens.
        Ability ability = new PowerUpAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
            new ManaCostsImpl<>("{5}{U}")
        );
        ability.addEffect(new CreateTokenEffect(new MerfolkToken(), 2));
        this.addAbility(ability);
    }

    private NamoraTheSeaQueen(final NamoraTheSeaQueen card) {
        super(card);
    }

    @Override
    public NamoraTheSeaQueen copy() {
        return new NamoraTheSeaQueen(this);
    }
}

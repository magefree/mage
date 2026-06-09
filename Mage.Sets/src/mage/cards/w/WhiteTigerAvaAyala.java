package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.PowerUpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.game.permanent.token.TigerGodToken;

/**
 *
 * @author muz
 */
public final class WhiteTigerAvaAyala extends CardImpl {

    public WhiteTigerAvaAyala(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Power-up -- {5}{G}: Put a +1/+1 counter on White Tiger and create The Tiger God, a legendary 4/4 green Cat God creature token with "The Tiger God can't be blocked by more than one creature."
        Ability ability = new PowerUpAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
            new ManaCostsImpl<>("{5}{G}")
        );
        ability.addEffect(new CreateTokenEffect(new TigerGodToken()).concatBy("and"));
        this.addAbility(ability);
    }

    private WhiteTigerAvaAyala(final WhiteTigerAvaAyala card) {
        super(card);
    }

    @Override
    public WhiteTigerAvaAyala copy() {
        return new WhiteTigerAvaAyala(this);
    }
}

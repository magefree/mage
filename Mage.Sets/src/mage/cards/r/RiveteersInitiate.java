package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RiveteersInitiate extends CardImpl {

    public RiveteersInitiate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.VIASHINO);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}{B/G}: Riveteers Initiate gains deathtouch until end of turn.
        this.addAbility(new SimpleActivatedAbility(new GainAbilitySourceEffect(
                DeathtouchAbility.getInstance(), Duration.EndOfTurn
        ), new ManaCostsImpl<>("{1}{B/G}")));
    }

    private RiveteersInitiate(final RiveteersInitiate card) {
        super(card);
    }

    @Override
    public RiveteersInitiate copy() {
        return new RiveteersInitiate(this);
    }
}

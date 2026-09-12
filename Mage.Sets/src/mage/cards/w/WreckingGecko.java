package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class WreckingGecko extends CardImpl {

    public WreckingGecko(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}")));

        // {6}{G}{G}: This creature gets +4/+4 and gains trample until end of turn.
        Ability ability = new SimpleActivatedAbility(
            new BoostSourceEffect(4, 4, Duration.EndOfTurn),
            new ManaCostsImpl<>("{6}{G}{G}")
        );
        ability.addEffect(new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.EndOfTurn));
        this.addAbility(ability);
    }

    private WreckingGecko(final WreckingGecko card) {
        super(card);
    }

    @Override
    public WreckingGecko copy() {
        return new WreckingGecko(this);
    }
}

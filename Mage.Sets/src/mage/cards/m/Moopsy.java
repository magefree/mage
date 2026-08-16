package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class Moopsy extends CardImpl {

    public Moopsy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // {2}: This creature gains reach until end of turn.
        this.addAbility(new SimpleActivatedAbility(
            new GainAbilitySourceEffect(ReachAbility.getInstance(), Duration.EndOfTurn),
            new ManaCostsImpl<>("{2}")
        ));
    }

    private Moopsy(final Moopsy card) {
        super(card);
    }

    @Override
    public Moopsy copy() {
        return new Moopsy(this);
    }
}

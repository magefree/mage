package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class FrogButler extends CardImpl {

    public FrogButler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.FROG);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // {2}: This creature gains reach until end of turn.
        this.addAbility(new SimpleActivatedAbility(
            new GainAbilitySourceEffect(ReachAbility.getInstance(), Duration.EndOfTurn),
            new ManaCostsImpl<>("{2}")
        ));
    }

    private FrogButler(final FrogButler card) {
        super(card);
    }

    @Override
    public FrogButler copy() {
        return new FrogButler(this);
    }
}

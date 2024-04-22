package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class PoisonDartFrog extends CardImpl {

    public PoisonDartFrog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        
        this.subtype.add(SubType.FROG);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // {2}: Poison Dart Frog gains deathtouch until end of turn.
        this.addAbility(new SimpleActivatedAbility(new GainAbilitySourceEffect(DeathtouchAbility.getInstance(), Duration.EndOfTurn), new GenericManaCost(2)));

    }

    private PoisonDartFrog(final PoisonDartFrog card) {
        super(card);
    }

    @Override
    public PoisonDartFrog copy() {
        return new PoisonDartFrog(this);
    }
}

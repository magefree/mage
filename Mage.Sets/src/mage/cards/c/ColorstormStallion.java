package mage.cards.c;

import mage.MageInt;
import mage.abilities.abilityword.OpusAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.CreateTokenCopySourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ColorstormStallion extends CardImpl {

    public ColorstormStallion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.HORSE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Ward {1}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{1}"), false));

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Opus -- Whenever you cast an instant or sorcery spell, this creature gets +1/+1 until end of turn. If five or more mana was spent to cast that spell, create a token that's a copy of this creature.
        this.addAbility(new OpusAbility(
                new BoostSourceEffect(1, 1, Duration.EndOfTurn),
                new CreateTokenCopySourceEffect(), null, false
        ));
    }

    private ColorstormStallion(final ColorstormStallion card) {
        super(card);
    }

    @Override
    public ColorstormStallion copy() {
        return new ColorstormStallion(this);
    }
}

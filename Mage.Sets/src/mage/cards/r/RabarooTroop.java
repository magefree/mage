package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.PlainscyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RabarooTroop extends CardImpl {

    public RabarooTroop(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.RABBIT);
        this.subtype.add(SubType.KANGAROO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Landfall -- Whenever a land you control enters, this creature gains flying until end of turn and you gain 1 life.
        Ability ability = new LandfallAbility(new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn));
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);

        // Plainscycling {2}
        this.addAbility(new PlainscyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private RabarooTroop(final RabarooTroop card) {
        super(card);
    }

    @Override
    public RabarooTroop copy() {
        return new RabarooTroop(this);
    }
}

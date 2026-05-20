package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.abilityword.ReparteeAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.LifelinkAbility;
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
public final class InkshapeDemonstrator extends CardImpl {

    public InkshapeDemonstrator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.ELEPHANT);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}")));

        // Repartee -- Whenever you cast an instant or sorcery spell that targets a creature, this creature gets +1/+0 and gains lifelink until end of turn.
        Ability ability = new ReparteeAbility(new BoostSourceEffect(
                1, 0, Duration.EndOfTurn
        ).setText("this creature gets +1/+0"));
        ability.addEffect(new GainAbilitySourceEffect(
                LifelinkAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains lifelink until end of turn"));
        this.addAbility(ability);
    }

    private InkshapeDemonstrator(final InkshapeDemonstrator card) {
        super(card);
    }

    @Override
    public InkshapeDemonstrator copy() {
        return new InkshapeDemonstrator(this);
    }
}

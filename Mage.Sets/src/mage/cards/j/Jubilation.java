package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.EncoreAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Jubilation extends CardImpl {

    public Jubilation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.INCARNATION);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // When this creature enters, creatures you control get +2/+2 and gain trample until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostControlledEffect(
                2, 2, Duration.EndOfTurn
        ).setText("creatures you control get +2/+2"));
        ability.addEffect(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURE
        ).setText("and gain trample until end of turn"));
        this.addAbility(ability);

        // Encore {7}{G}{G}
        this.addAbility(new EncoreAbility(new ManaCostsImpl<>("{7}{G}{G}")));
    }

    private Jubilation(final Jubilation card) {
        super(card);
    }

    @Override
    public Jubilation copy() {
        return new Jubilation(this);
    }
}

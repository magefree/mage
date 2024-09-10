package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.AddCardTypeSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.CrewAbility;
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
public final class MizziumTank extends CardImpl {

    public MizziumTank(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{R}{R}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever you cast a noncreature spell, Mizzium Tank becomes an artifact creature and gets +1/+1 until end of turn.
        Ability ability = new SpellCastControllerTriggeredAbility(new AddCardTypeSourceEffect(
                Duration.EndOfTurn, CardType.ARTIFACT, CardType.CREATURE
        ).setText("{this} becomes an artifact creature"), StaticFilters.FILTER_SPELL_A_NON_CREATURE, false);
        ability.addEffect(new BoostSourceEffect(
                1, 1, Duration.EndOfTurn
        ).setText("and gets +1/+1 until end of turn"));
        this.addAbility(ability);

        // Crew 1
        this.addAbility(new CrewAbility(1));
    }

    private MizziumTank(final MizziumTank card) {
        super(card);
    }

    @Override
    public MizziumTank copy() {
        return new MizziumTank(this);
    }
}

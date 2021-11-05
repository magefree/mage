package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.HasteAbility;
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
public final class FrenziedDevils extends CardImpl {

    public FrenziedDevils(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.DEVIL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever you cast a noncreature spell, Frenzied Devils gets +2/+2 until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new BoostSourceEffect(2, 2, Duration.EndOfTurn),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ));
    }

    private FrenziedDevils(final FrenziedDevils card) {
        super(card);
    }

    @Override
    public FrenziedDevils copy() {
        return new FrenziedDevils(this);
    }
}

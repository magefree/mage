package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class StatureYoungAvenger extends CardImpl {

    public StatureYoungAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R/G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever you cast a noncreature spell, Stature has base power and toughness 4/4 until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(
            new SetBasePowerToughnessSourceEffect(4, 4, Duration.EndOfTurn),
            StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ));
    }

    private StatureYoungAvenger(final StatureYoungAvenger card) {
        super(card);
    }

    @Override
    public StatureYoungAvenger copy() {
        return new StatureYoungAvenger(this);
    }
}

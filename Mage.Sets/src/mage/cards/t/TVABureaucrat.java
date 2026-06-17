package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class TVABureaucrat extends CardImpl {

    public TVABureaucrat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever you cast a noncreature spell, this creature gets +1/+0 until end of turn and can't be blocked this turn.
        Ability ability = new SpellCastControllerTriggeredAbility(
            new BoostSourceEffect(1, 0, Duration.EndOfTurn),
            StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        );
        ability.addEffect(new CantBeBlockedSourceEffect(Duration.EndOfTurn).concatBy("and"));
        this.addAbility(ability);
    }

    private TVABureaucrat(final TVABureaucrat card) {
        super(card);
    }

    @Override
    public TVABureaucrat copy() {
        return new TVABureaucrat(this);
    }
}

package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 * @author TheElk801
 */
public final class BurningProphet extends CardImpl {

    public BurningProphet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever you cast a noncreature spell, Burning Prophet gets +1/+0 until end of turn, then scry 1.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new BoostSourceEffect(1, 0, Duration.EndOfTurn),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        );
        ability.addEffect(new ScryEffect(1, false).concatBy(", then"));
        this.addAbility(ability);
    }

    private BurningProphet(final BurningProphet card) {
        super(card);
    }

    @Override
    public BurningProphet copy() {
        return new BurningProphet(this);
    }
}

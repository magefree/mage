package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
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
public final class FestivalCrasher extends CardImpl {

    public FestivalCrasher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.DEVIL);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever you cast an instant or sorcery spell, Festival Crasher gets +2/+0 until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new BoostSourceEffect(2, 0, Duration.EndOfTurn),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        ));
    }

    private FestivalCrasher(final FestivalCrasher card) {
        super(card);
    }

    @Override
    public FestivalCrasher copy() {
        return new FestivalCrasher(this);
    }
}

package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
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
public final class SkyTheaterStrix extends CardImpl {

    public SkyTheaterStrix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast a noncreature spell, Sky Theater Strix gets +1/+0 until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new BoostSourceEffect(1, 0, Duration.EndOfTurn),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ));
    }

    private SkyTheaterStrix(final SkyTheaterStrix card) {
        super(card);
    }

    @Override
    public SkyTheaterStrix copy() {
        return new SkyTheaterStrix(this);
    }
}

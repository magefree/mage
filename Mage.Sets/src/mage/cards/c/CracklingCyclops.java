package mage.cards.c;

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
public final class CracklingCyclops extends CardImpl {

    public CracklingCyclops(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.CYCLOPS);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Whenever you cast a noncreature spell, this creature gets +3/+0 until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new BoostSourceEffect(3, 0, Duration.EndOfTurn),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ));
    }

    private CracklingCyclops(final CracklingCyclops card) {
        super(card);
    }

    @Override
    public CracklingCyclops copy() {
        return new CracklingCyclops(this);
    }
}


package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.CreatureExploresTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class LurkingChupacabra extends CardImpl {

    public LurkingChupacabra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.BEAST);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever a creature you control explores, target creature an opponent controls gets -2/-2 until end of turn
        Ability ability = new CreatureExploresTriggeredAbility(new BoostTargetEffect(-2, -2, Duration.EndOfTurn));
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));
        this.addAbility(ability);
    }

    private LurkingChupacabra(final LurkingChupacabra card) {
        super(card);
    }

    @Override
    public LurkingChupacabra copy() {
        return new LurkingChupacabra(this);
    }
}


package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FathomFleetCutthroat extends CardImpl {

    public FathomFleetCutthroat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Fathom Fleet Cutthroat enters the battlefield, destroy target creature an opponent controls that was dealt damage this turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(), false);
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_OPPONENTS_CREATURE_DAMAGED_THIS_TURN));
        this.addAbility(ability);
    }

    private FathomFleetCutthroat(final FathomFleetCutthroat card) {
        super(card);
    }

    @Override
    public FathomFleetCutthroat copy() {
        return new FathomFleetCutthroat(this);
    }
}

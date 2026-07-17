package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.BecomePreparedTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SkycoachWaypoint extends CardImpl {

    public SkycoachWaypoint(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {3}, {T}: Target creature becomes prepared.
        Ability ability = new SimpleActivatedAbility(new BecomePreparedTargetEffect(true), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private SkycoachWaypoint(final SkycoachWaypoint card) {
        super(card);
    }

    @Override
    public SkycoachWaypoint copy() {
        return new SkycoachWaypoint(this);
    }
}

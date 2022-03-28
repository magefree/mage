
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author L_J
 */
public final class DoomCannon extends CardImpl {

    public DoomCannon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        // As Doom Cannon enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.Sacrifice)));

        // {3}, {T}, Sacrifice a creature of the chosen type: Doom Cannon deals 3 damage to any target.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(3), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(new DoomCannonFilter())));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private DoomCannon(final DoomCannon card) {
        super(card);
    }

    @Override
    public DoomCannon copy() {
        return new DoomCannon(this);
    }
}

class DoomCannonFilter extends FilterControlledCreaturePermanent {

    public DoomCannonFilter() {
        super("a creature of the chosen type");
    }

    public DoomCannonFilter(final DoomCannonFilter filter) {
        super(filter);
    }

    @Override
    public DoomCannonFilter copy() {
        return new DoomCannonFilter(this);
    }

    @Override
    public boolean match(Permanent permanent, UUID playerId, Ability source, Game game) {
        if (super.match(permanent, playerId, source, game)) {
            SubType subType = ChooseCreatureTypeEffect.getChosenCreatureType(source.getSourceId(), game);
            if (subType != null && permanent.hasSubtype(subType, game)) {
                return true;
            }
        }
        return false;
    }

}

package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.GreatestSharedCreatureTypeCount;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SecretTunnel extends CardImpl {

    public SecretTunnel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.CAVE);

        // This land can't be blocked.
        this.addAbility(new CantBeBlockedSourceAbility());

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {4}, {T}: Two target creatures you control that share a creature type can't be blocked this turn.
        Ability ability = new SimpleActivatedAbility(new CantBeBlockedTargetEffect(), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new SecretTunnelTarget());
        this.addAbility(ability);
    }

    private SecretTunnel(final SecretTunnel card) {
        super(card);
    }

    @Override
    public SecretTunnel copy() {
        return new SecretTunnel(this);
    }
}

class SecretTunnelTarget extends TargetPermanent {
    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("creatures you control that share a creature type");

    SecretTunnelTarget() {
        super(2, 2, filter, false);
    }

    private SecretTunnelTarget(final SecretTunnelTarget target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(playerId, id, source, game)) {
            return false;
        }
        if (getTargets().isEmpty()) {
            return true;
        }
        Permanent targetOne = game.getPermanent(getTargets().get(0));
        Permanent targetTwo = game.getPermanent(id);
        if (targetOne == null || targetTwo == null) {
            return false;
        }
        return targetOne.shareCreatureTypes(game, targetTwo);
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Ability source, Game game) {
        return GreatestSharedCreatureTypeCount.getValue(sourceControllerId, source, game) >= 2;
    }

    @Override
    public SecretTunnelTarget copy() {
        return new SecretTunnelTarget(this);
    }
}

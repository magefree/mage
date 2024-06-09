package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.List;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class VolatileRig extends CardImpl {

    public VolatileRig(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");
        this.subtype.add(SubType.CONSTRUCT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Volatile Rig attacks each turn if able.
        this.addAbility(new AttacksEachCombatStaticAbility());

        // Whenever Volatile Rig is dealt damage, flip a coin. If you lose the flip, sacrifice Volatile Rig.
        this.addAbility(new DealtDamageToSourceTriggeredAbility(new VolatileRigEffect(), false));

        // When Volatile Rig dies, flip a coin. If you lose the flip, it deals 4 damage to each creature and each player.
        this.addAbility(new DiesSourceTriggeredAbility(new VolatileRigEffect2()));

    }

    private VolatileRig(final VolatileRig card) {
        super(card);
    }

    @Override
    public VolatileRig copy() {
        return new VolatileRig(this);
    }
}

class VolatileRigEffect extends OneShotEffect {

    VolatileRigEffect() {
        super(Outcome.Sacrifice);
        staticText = "flip a coin. If you lose the flip, sacrifice {this}";
    }

    private VolatileRigEffect(final VolatileRigEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            if (!player.flipCoin(source, game, true)) {
                Permanent permanent = game.getPermanent(source.getSourceId());
                if (permanent != null) {
                    return permanent.sacrifice(source, game);
                }
            }
        }
        return false;
    }

    @Override
    public VolatileRigEffect copy() {
        return new VolatileRigEffect(this);
    }
}

class VolatileRigEffect2 extends OneShotEffect {

    VolatileRigEffect2() {
        super(Outcome.Sacrifice);
        staticText = "flip a coin. If you lose the flip, it deals 4 damage to each creature and each player";
    }

    private VolatileRigEffect2(final VolatileRigEffect2 effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            if (!player.flipCoin(source, game, true)) {

                List<Permanent> permanents = game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game);
                for (Permanent permanent : permanents) {
                    permanent.damage(4, source.getSourceId(), source, game, false, true);
                }
                for (UUID playerId : game.getState().getPlayersInRange(player.getId(), game)) {
                    Player damageToPlayer = game.getPlayer(playerId);
                    if (damageToPlayer != null) {
                        damageToPlayer.damage(4, source.getSourceId(), source, game);
                    }
                }
                return true;

            }
        }
        return false;
    }

    @Override
    public VolatileRigEffect2 copy() {
        return new VolatileRigEffect2(this);
    }
}

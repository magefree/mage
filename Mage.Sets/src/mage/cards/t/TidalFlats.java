package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author L_J
 */
public final class TidalFlats extends CardImpl {

    public TidalFlats(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");

        // {U}{U}: For each attacking creature without flying, its controller may pay {1}. If they don't, creatures you control blocking that creature gain first strike until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new TidalFlatsEffect(), new ManaCostsImpl<>("{U}{U}")));
    }

    private TidalFlats(final TidalFlats card) {
        super(card);
    }

    @Override
    public TidalFlats copy() {
        return new TidalFlats(this);
    }
}

class TidalFlatsEffect extends OneShotEffect {

    private static final FilterAttackingCreature filter = new FilterAttackingCreature("attacking creature without flying");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public TidalFlatsEffect() {
        super(Outcome.Benefit);
        this.staticText = "For each attacking creature without flying, its controller may pay {1}. If they don't, creatures you control blocking that creature gain first strike until end of turn";
    }

    public TidalFlatsEffect(final TidalFlatsEffect effect) {
        super(effect);
    }

    @Override
    public TidalFlatsEffect copy() {
        return new TidalFlatsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Player player = game.getPlayer(game.getActivePlayerId());
        if (player == null) {
            return false;
        }
        Cost cost = new ManaCostsImpl<>("{1}");
        List<Permanent> affectedPermanents = new ArrayList<>();
        for (Permanent permanent : game.getState().getBattlefield().getAllActivePermanents(filter, player.getId(), game)) {
            cost.clearPaid();
            String message = "Pay " + cost.getText() + " for " + permanent.getLogName() + "? If you don't, creatures " + controller.getLogName() + " controls blocking it gain first strike until end of turn.";
            if (player.chooseUse(Outcome.Benefit, message, source, game)) {
                if (cost.pay(source, game, source, player.getId(), false, null)) {
                    game.informPlayers(player.getLogName() + " paid " + cost.getText() + " for " + permanent.getLogName());
                } else {
                    game.informPlayers(player.getLogName() + " didn't pay " + cost.getText() + " for " + permanent.getLogName());
                    affectedPermanents.add(permanent);
                }
            } else {
                game.informPlayers(player.getLogName() + " didn't pay " + cost.getText() + " for " + permanent.getLogName());
                affectedPermanents.add(permanent);
            }
        }

        for (Permanent permanent : affectedPermanents) {
            CombatGroup group = game.getCombat().findGroup(permanent.getId());
            if (group != null) {
                for (UUID blockerId : group.getBlockers()) {
                    Permanent blocker = game.getPermanent(blockerId);
                    if (blocker != null && Objects.equals(blocker.getControllerId(), controller.getId())) {
                        ContinuousEffect effect = new GainAbilityTargetEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn);
                        effect.setTargetPointer(new FixedTarget(blocker.getId(), game));
                        game.addEffect(effect, source);
                    }
                }
            }

        }
        return true;
    }
}

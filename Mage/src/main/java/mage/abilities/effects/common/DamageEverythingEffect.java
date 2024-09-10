package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.List;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class DamageEverythingEffect extends OneShotEffect {

    private DynamicValue amount;
    private FilterPermanent filter;
    private UUID damageSource;
    private String sourceName = "{this}";

    public DamageEverythingEffect(int amount) {
        this(StaticValue.get(amount), new FilterCreaturePermanent());
    }

    public DamageEverythingEffect(int amount, String whoDealDamageName) {
        this(StaticValue.get(amount), new FilterCreaturePermanent());

        this.sourceName = whoDealDamageName;
        setText(); // TODO: replace to @Override public String getText()
    }

    public DamageEverythingEffect(DynamicValue amount) {
        this(amount, new FilterCreaturePermanent());
    }

    public DamageEverythingEffect(int amount, FilterPermanent filter) {
        this(StaticValue.get(amount), filter);
    }

    public DamageEverythingEffect(DynamicValue amount, FilterPermanent filter) {
        this(amount, filter, null);
    }

    public DamageEverythingEffect(DynamicValue amount, FilterPermanent filter, UUID damageSource) {
        super(Outcome.Damage);
        this.amount = amount;
        this.filter = filter;
        this.damageSource = damageSource;
        setText();
    }

    private void setText() {
        staticText = this.sourceName + " deals " + this.amount.toString() + " damage to each " + this.filter.getMessage() + " and each player";
    }

    protected DamageEverythingEffect(final DamageEverythingEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.filter = effect.filter;
        this.damageSource = effect.damageSource;
        this.sourceName = effect.sourceName;
    }

    @Override
    public DamageEverythingEffect copy() {
        return new DamageEverythingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int damage = amount.calculate(game, source, this);
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game);
        for (Permanent permanent : permanents) {
            permanent.damage(damage, damageSource == null ? source.getSourceId() : damageSource, source, game, false, true);
        }
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.damage(damage, damageSource == null ? source.getSourceId() : damageSource, source, game);
            }
        }
        return true;
    }
}

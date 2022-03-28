
package mage.abilities.effects.common;

import java.util.List;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DamageAllEffect extends OneShotEffect {

    private FilterPermanent filter;
    private DynamicValue amount;
    private String sourceName = "{this}";

    public DamageAllEffect(int amount, FilterPermanent filter) {
        this(StaticValue.get(amount), filter);
    }

    public DamageAllEffect(int amount, String whoDealDamageName, FilterPermanent filter) {
        this(StaticValue.get(amount), filter);

        this.sourceName = whoDealDamageName;
        setText(); // TODO: replace to @Override public String getText()
    }

    public DamageAllEffect(DynamicValue amount, FilterPermanent filter) {
        super(Outcome.Damage);
        this.amount = amount;
        this.filter = filter;

        setText();
    }

    public DamageAllEffect(final DamageAllEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.filter = effect.filter.copy();
        this.sourceName = effect.sourceName;
    }

    @Override
    public DamageAllEffect copy() {
        return new DamageAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game);
        for (Permanent permanent : permanents) {
            permanent.damage(amount.calculate(game, source, this), source.getSourceId(), source, game, false, true);
        }
        return true;
    }

    public void setText() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.sourceName).append(" deals ").append(amount.toString()).append(" damage to each ").append(filter.getMessage());
        String message = amount.getMessage();
        if (!message.isEmpty()) {
            if (amount.toString().equals("X")) {
                sb.append(", where X is ");
            } else {
                sb.append(" for each ");
            }
            sb.append(message);
        }
        staticText = sb.toString();
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }
}

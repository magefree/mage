package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.PreventionEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.FilterSource;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.TargetSource;
import mage.util.CardUtil;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author Quercitron, Susucr
 */
public class PreventNextDamageFromChosenSourceEffect extends PreventionEffectImpl {

    protected final Target target;
    private final boolean toYou;
    private final ApplierOnPrevention onPrevention;

    public interface ApplierOnPrevention extends Serializable {
        boolean apply(PreventionEffectData data, Target target, GameEvent event, Ability source, Game game);

        String getText();
    }

    public static final ApplierOnPrevention ON_PREVENT_YOU_GAIN_THAT_MUCH_LIFE = new ApplierOnPrevention() {
        public String getText() {
            return "You gain life equal to the damage prevented this way";
        }

        public boolean apply(PreventionEffectData data, Target target, GameEvent event, Ability source, Game game) {
            if (data == null || data.getPreventedDamage() <= 0) {
                return false;
            }
            int prevented = data.getPreventedDamage();
            Player controller = game.getPlayer(source.getControllerId());
            if (controller == null) {
                return false;
            }
            controller.gainLife(prevented, game, source);
            return true;
        }
    };

    public PreventNextDamageFromChosenSourceEffect(Duration duration, boolean toYou) {
        this(duration, toYou, new FilterSource());
    }

    public PreventNextDamageFromChosenSourceEffect(Duration duration, boolean toYou, FilterSource filterSource) {
        this(duration, toYou, filterSource, false, null);
    }

    public PreventNextDamageFromChosenSourceEffect(Duration duration, boolean toYou, ApplierOnPrevention onPrevention) {
        this(duration, toYou, new FilterSource(), onPrevention);
    }

    public PreventNextDamageFromChosenSourceEffect(Duration duration, boolean toYou, FilterSource filterSource, ApplierOnPrevention onPrevention) {
        this(duration, toYou, filterSource, false, onPrevention);
    }

    public PreventNextDamageFromChosenSourceEffect(Duration duration, boolean toYou, FilterSource filterSource, boolean onlyCombat, ApplierOnPrevention onPrevention) {
        this(duration, toYou, new TargetSource(filterSource), onlyCombat, onPrevention);
    }

    public PreventNextDamageFromChosenSourceEffect(Duration duration, boolean toYou, FilterPermanent filterPermanent) {
        this(duration, toYou, filterPermanent, false, null);
    }

    public PreventNextDamageFromChosenSourceEffect(Duration duration, boolean toYou, FilterPermanent filterPermanent, boolean onlyCombat, ApplierOnPrevention onPrevention) {
        this(duration, toYou, new TargetPermanent(filterPermanent), onlyCombat, onPrevention);
    }

    private PreventNextDamageFromChosenSourceEffect(Duration duration, boolean toYou, Target target, boolean onlyCombat, ApplierOnPrevention onPrevention) {
        super(duration, Integer.MAX_VALUE, onlyCombat);
        this.target = target;
        this.toYou = toYou;
        this.onPrevention = onPrevention;
        this.staticText = setText();
    }

    protected PreventNextDamageFromChosenSourceEffect(final PreventNextDamageFromChosenSourceEffect effect) {
        super(effect);
        this.target = effect.target.copy();
        this.toYou = effect.toYou;
        this.onPrevention = effect.onPrevention;
    }

    @Override
    public PreventNextDamageFromChosenSourceEffect copy() {
        return new PreventNextDamageFromChosenSourceEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        UUID controllerId = source.getControllerId();
        if (this.target.canChoose(controllerId, source, game)) {
            this.target.choose(Outcome.PreventDamage, controllerId, source.getSourceId(), source, game);
        }
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        PreventionEffectData data = preventDamageAction(event, source, game);
        discard();
        if (onPrevention != null) {
            onPrevention.apply(data, target, event, source, game);
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return super.applies(event, source, game)
                && (!toYou || event.getTargetId().equals(source.getControllerId()))
                && target.getTargets().contains(event.getSourceId());
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("The next time ")
                .append(CardUtil.addArticle(target.getFilter().getMessage()));
        sb.append(" of your choice would deal damage");
        if (toYou) {
            sb.append(" to you");
        }
        if (duration == Duration.EndOfTurn) {
            sb.append(" this turn");
        }
        sb.append(", prevent that damage");
        if (onPrevention != null) {
            sb.append(". ").append(onPrevention.getText());
        }
        return sb.toString();
    }

}

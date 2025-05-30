package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.PreventionEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterObject;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetSource;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Quercitron, Susucr
 */
public class PreventNextDamageFromChosenSourceEffect extends PreventionEffectImpl {

    protected final TargetSource targetSource;
    private final boolean toYou;
    private final ApplierOnPrevention onPrevention;

    public interface ApplierOnPrevention {
        boolean apply(PreventionEffectData data, TargetSource targetsource, GameEvent event, Ability source, Game game);

        String getText();
    }

    public static final ApplierOnPrevention ON_PREVENT_YOU_GAIN_THAT_MUCH_LIFE = new ApplierOnPrevention() {
        public String getText() {
            return "You gain life equal to the damage prevented this way";
        }

        public boolean apply(PreventionEffectData data, TargetSource targetSource, GameEvent event, Ability source, Game game) {
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
        this(duration, toYou, new FilterObject("source"));
    }

    public PreventNextDamageFromChosenSourceEffect(Duration duration, boolean toYou, FilterObject filter) {
        this(duration, toYou, filter, false, null);
    }

    public PreventNextDamageFromChosenSourceEffect(Duration duration, boolean toYou, ApplierOnPrevention onPrevention) {
        this(duration, toYou, new FilterObject("source"), onPrevention);
    }

    public PreventNextDamageFromChosenSourceEffect(Duration duration, boolean toYou, FilterObject filter, ApplierOnPrevention onPrevention) {
        this(duration, toYou, filter, false, onPrevention);
    }

    public PreventNextDamageFromChosenSourceEffect(Duration duration, boolean toYou, FilterObject filter, boolean onlyCombat, ApplierOnPrevention onPrevention) {
        super(duration, Integer.MAX_VALUE, onlyCombat);
        this.targetSource = new TargetSource(filter);
        this.toYou = toYou;
        this.onPrevention = onPrevention;
        this.staticText = setText();
    }

    protected PreventNextDamageFromChosenSourceEffect(final PreventNextDamageFromChosenSourceEffect effect) {
        super(effect);
        this.targetSource = effect.targetSource.copy();
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
        if (this.targetSource.canChoose(controllerId, source, game)) {
            this.targetSource.choose(Outcome.PreventDamage, controllerId, source.getSourceId(), source, game);
        }
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        PreventionEffectData data = preventDamageAction(event, source, game);
        this.used = true;
        if (onPrevention != null) {
            onPrevention.apply(data, targetSource, event, source, game);
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return !this.used
                && super.applies(event, source, game)
                && (!toYou || event.getTargetId().equals(source.getControllerId()))
                && event.getSourceId().equals(targetSource.getFirstTarget());
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("The next time ")
                .append(CardUtil.addArticle(targetSource.getFilter().getMessage()));
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

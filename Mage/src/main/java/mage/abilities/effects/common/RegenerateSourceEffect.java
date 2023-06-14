package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.hint.Hint;
import mage.abilities.hint.HintUtils;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com, JayDi85
 */
public class RegenerateSourceEffect extends ReplacementEffectImpl {

    public RegenerateSourceEffect() {
        this("{this}");
    }

    public RegenerateSourceEffect(String targetName) {
        super(Duration.EndOfTurn, Outcome.Regenerate);
        staticText = "regenerate " + targetName;
    }

    public RegenerateSourceEffect(final RegenerateSourceEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        //20110204 - 701.11
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null
                && permanent.regenerate(source, game)) {
            this.used = true;
            discard();
            return true;
        }
        return false;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);

        RegenerateSourceEffect.initRegenerationShieldInfo(game, source, source.getSourceId());
    }

    @Override
    public RegenerateSourceEffect copy() {
        return new RegenerateSourceEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return apply(game, source);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DESTROY_PERMANENT
                || event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        // The regeneration effect is discarded if the permanent is blinked or changes zone
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE
                && event.getTargetId() == source.getSourceId()) {
            discard();
            return false;
        }
        //20110204 - 701.11c - event.getAmount() is used to signal if regeneration is allowed
        return event.getAmount() == 0
                && event.getTargetId().equals(source.getSourceId())
                && !this.used;
    }

    /**
     * Add info about new regen shield.
     * Warning, it's a workaround to show regen shields info, real effects will be use replacement logic.
     *
     * @param game
     * @param source
     * @param permanentId
     */
    public static void initRegenerationShieldInfo(Game game, Ability source, UUID permanentId) {
        Permanent permanent = game.getPermanent(permanentId);
        if (permanent != null) {
            // add one regen shield
            RegenerateSourceEffect.incRegenerationShieldsAmount(game, permanent.getId());
            // add regen info
            InfoEffect.addCardHintToPermanent(game, source, permanent,
                    RegenerationShieldsHint.instance, Duration.EndOfTurn);
        }
    }

    public static int getRegenerationShieldsAmount(Game game, UUID permanentId) {
        // info must be reset on new turn
        Integer amount = (Integer) game.getState().getValue(
                CardUtil.getCardZoneString("RegenerationShieldsAmount_turn" + game.getTurnNum(), permanentId, game));
        if (amount != null) {
            return amount;
        }
        return 0;
    }

    private static void setRegenerationShieldsAmount(Game game, UUID permanentId, int amount) {
        game.getState().setValue(
                CardUtil.getCardZoneString("RegenerationShieldsAmount_turn" + game.getTurnNum(), permanentId, game), amount);
    }

    public static int incRegenerationShieldsAmount(Game game, UUID permanentId) {
        int amount = getRegenerationShieldsAmount(game, permanentId) + 1;
        setRegenerationShieldsAmount(game, permanentId, amount);
        return amount;
    }

    public static int decRegenerationShieldsAmount(Game game, UUID permanentId) {
        int amount = Math.max(0, getRegenerationShieldsAmount(game, permanentId) - 1);
        setRegenerationShieldsAmount(game, permanentId, amount);
        return amount;
    }
}

enum RegenerationShieldsHint implements Hint {

    instance;

    @Override
    public String getText(Game game, Ability ability) {
        int amount = RegenerateSourceEffect.getRegenerationShieldsAmount(game, ability.getSourceId());
        String info = "Regeneration shields: " + amount + " (permanent will be regenerated instead of destroyed)";
        if (amount > 0) {
            return HintUtils.prepareText(info, null, HintUtils.HINT_ICON_GOOD);
        } else {
            return HintUtils.prepareText(info, null, HintUtils.HINT_ICON_BAD);
        }
    }

    @Override
    public Hint copy() {
        return instance;
    }
}

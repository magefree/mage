package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.StaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.hint.common.DayNightHint;
import mage.cards.Card;
import mage.constants.*;
import mage.game.Game;

/**
 * @author TheElk801
 */
public class NightboundAbility extends StaticAbility {

    public NightboundAbility() {
        super(Zone.BATTLEFIELD, new NightboundEffect());
        this.addHint(DayNightHint.instance);
    }

    private NightboundAbility(final NightboundAbility ability) {
        super(ability);
    }

    @Override
    public String getRule() {
        return "nightbound <i>(If a player casts at least two spells during their own turn, it becomes day next turn.)</i>";
    }

    @Override
    public NightboundAbility copy() {
        return new NightboundAbility(this);
    }

    public static boolean checkCard(Card card, Game game) {
        return game.checkDayNight(false)
                && card.getSecondCardFace() != null
                && card.getSecondCardFace().getAbilities().containsClass(NightboundAbility.class);
    }
}

class NightboundEffect extends ContinuousEffectImpl {

    NightboundEffect() {
        super(Duration.WhileOnBattlefield, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
    }

    private NightboundEffect(final NightboundEffect effect) {
        super(effect);
    }

    @Override
    public NightboundEffect copy() {
        return new NightboundEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!game.hasDayNight()) {
            game.setDaytime(false);
        }
        return true;
    }
}

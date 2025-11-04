package mage.abilities.common;

import mage.abilities.effects.common.RoomCharacteristicsEffect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentImpl;

// For the overall Room card flavor text and mana value effect.
public class RoomAbility extends SimpleStaticAbility {
    public RoomAbility() {
        super(Zone.BATTLEFIELD, new RoomCharacteristicsEffect());
        this.setRuleVisible(true);
        this.setRuleAtTheTop(true);
    }

    protected RoomAbility(final RoomAbility ability) {
        super(ability);
    }

    @Override
    public String getRule() {
        return "<i>(You may cast either half. That door unlocks on the battlefield. " +
                "As a sorcery, you may pay the mana cost of a locked door to unlock it.)</i>";
    }

    @Override
    public RoomAbility copy() {
        return new RoomAbility(this);
    }

    public void applyCharacteristics(Game game, Permanent permanent) {
        ((RoomCharacteristicsEffect) this.getEffects().get(0)).removeCharacteristics(game, permanent);
    }

    public void restoreUnlockedStats(Game game, PermanentImpl permanent) {
        ((RoomCharacteristicsEffect) this.getEffects().get(0)).restoreUnlockedStats(game, permanent);
    }
}

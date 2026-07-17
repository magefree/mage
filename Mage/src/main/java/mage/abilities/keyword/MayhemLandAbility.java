package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.StaticAbility;
import mage.abilities.effects.AsThoughEffect;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.constants.*;
import mage.game.Game;

import java.util.UUID;

public  class MayhemLandAbility extends StaticAbility {

        private final String rule;

        public MayhemLandAbility() {
            super(AbilityType.STATIC, Zone.GRAVEYARD);
            this.newId();
            this.addEffect(new MayhemPlayEffect());
            this.addWatcher(new MayhemWatcher());
            this.setRuleAtTheTop(true);
            this.rule = "Mayhem " +
                    " <i>(You may play this card from your graveyard if you discarded it this turn. " +
                    "Timing rules still apply.)</i>";
        }

        protected MayhemLandAbility(final MayhemLandAbility ability) {
            super(ability);
            this.rule = ability.rule;
        }

        @Override
        public MayhemLandAbility copy() {
            return new MayhemLandAbility(this);
        }

        @Override
        public String getRule() {
            return rule;
        }
}

class MayhemPlayEffect extends AsThoughEffectImpl {

    public MayhemPlayEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileInGraveyard, Outcome.Neutral);
    }

    public MayhemPlayEffect(final MayhemPlayEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        return Zone.GRAVEYARD.match(game.getState().getZone(sourceId))
                && MayhemWatcher.checkCard(sourceId, game);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public AsThoughEffect copy() {
        return new MayhemPlayEffect(this);
    }
}
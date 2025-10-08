package mage.abilities.keyword;

import mage.MageIdentifier;
import mage.abilities.PlayLandAbility;
import mage.cards.Card;
import mage.constants.Zone;
import mage.game.Game;

import java.util.Set;
import java.util.UUID;

public  class MayhemLandAbility extends PlayLandAbility {

        private final String rule;

        public MayhemLandAbility(Card card) {
            super(card.getName());
            this.zone = Zone.GRAVEYARD;
            this.newId();
            this.name += " with Mayhem";
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
        public ActivationStatus canActivate(UUID playerId, Game game) {
            if (!Zone.GRAVEYARD.match(game.getState().getZone(getSourceId()))
                    || !MayhemWatcher.checkCard(getSourceId(), game)) {
                return ActivationStatus.getFalse();
            }
            return super.canActivate(playerId, game);
        }

        @Override
        public boolean activate(Game game, Set<MageIdentifier> allowedIdentifiers, boolean noMana) {
            if (!super.activate(game, allowedIdentifiers, noMana)) {
                return false;
            }
            this.setCostsTag(MayhemAbility.MAYHEM_ACTIVATION_VALUE_KEY, null);
            return true;
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
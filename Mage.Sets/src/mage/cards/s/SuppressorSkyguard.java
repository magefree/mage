package mage.cards.s;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.PreventDamageToControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.events.GameEvent;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class SuppressorSkyguard extends CardImpl {
    public SuppressorSkyguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");

        this.addSubType(SubType.HUMAN);
        this.addSubType(SubType.KNIGHT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // whenever a player attacks you, if that player has another opponent who isn't being attacked, prevent all combat damage that would be dealt to you this combat
        this.addAbility(new SuppressorSkyguardTriggerAttackYou());
    }

    public SuppressorSkyguard(SuppressorSkyguard card) {
        super(card);
    }

    @Override
    public SuppressorSkyguard copy() {
        return new SuppressorSkyguard(this);
    }

    class SuppressorSkyguardTriggerAttackYou extends TriggeredAbilityImpl {
        SuppressorSkyguardTriggerAttackYou() {
            super(Zone.BATTLEFIELD, new PreventDamageToControllerEffect(Duration.EndOfCombat, true, false, Integer.MAX_VALUE), false);
            this.setTriggerPhrase("whenever a player attacks you, ");
        }

        private SuppressorSkyguardTriggerAttackYou(final SuppressorSkyguardTriggerAttackYou ability) {
            super(ability);
        }

        @Override
        public SuppressorSkyguardTriggerAttackYou copy() {
            return new SuppressorSkyguardTriggerAttackYou(this);
        }

        @Override
        public boolean checkEventType(GameEvent event, Game game) {
            return event.getType() == GameEvent.EventType.DEFENDER_ATTACKED;
        }

        @Override
        public boolean checkTrigger(GameEvent event, Game game) {
            return getControllerId().equals(event.getTargetId());
        }

        @Override
        public boolean checkInterveningIfClause(Game game) {
            UUID activePlayerId = game.getActivePlayerId();
            Set<UUID> opponents = game.getOpponents(activePlayerId);
            game.getCombat()
                    .getGroups()
                    .stream()
                    .map(CombatGroup::getDefenderId)
                    .filter(Objects::nonNull)
                    .distinct()
                    .forEach(opponents::remove);

            return !opponents.isEmpty();
        }

        @Override
        public String getRule() {
            return "Whenever a player attacks you, if that player has another opponent who isn't being attacked, prevent all combat damage that would be dealt to you this combat.";
        }
    }
}

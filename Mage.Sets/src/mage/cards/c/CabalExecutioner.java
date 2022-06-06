
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author fireshoes
 */
public final class CabalExecutioner extends CardImpl {

    public CabalExecutioner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Cabal Executioner deals combat damage to a player, that player sacrifices a creature.
        this.addAbility(new CabalExecutionerAbility());

        // Morph {3}{B}{B}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{3}{B}{B}")));
    }

    private CabalExecutioner(final CabalExecutioner card) {
        super(card);
    }

    @Override
    public CabalExecutioner copy() {
        return new CabalExecutioner(this);
    }
}

class CabalExecutionerAbility extends TriggeredAbilityImpl {

    public CabalExecutionerAbility() {
        super(Zone.BATTLEFIELD, new SacrificeEffect(StaticFilters.FILTER_PERMANENT_CREATURE, 1, ""));
    }

    public CabalExecutionerAbility(final CabalExecutionerAbility ability) {
        super(ability);
    }

    @Override
    public CabalExecutionerAbility copy() {
        return new CabalExecutionerAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
        if (damageEvent.isCombatDamage() && event.getSourceId().equals(this.getSourceId())) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a player, that player sacrifices a creature.";
    }
}

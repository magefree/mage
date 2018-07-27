package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.common.ZoneChangeTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeDefendingPlayerEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TurnPhase;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;

/**
 *
 * @author NinthWorld
 */
public final class InfestedTerran extends CardImpl {

    public InfestedTerran(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        
        this.subtype.add(SubType.ZERG);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Infested Terran dies during combat, if it was attacking, defending player loses 3 life.
        this.addAbility(new InfestedTerranAbility());
    }

    public InfestedTerran(final InfestedTerran card) {
        super(card);
    }

    @Override
    public InfestedTerran copy() {
        return new InfestedTerran(this);
    }
}

class InfestedTerranAbility extends ZoneChangeTriggeredAbility {

    public InfestedTerranAbility() {
        super(Zone.BATTLEFIELD, Zone.GRAVEYARD, new LoseLifeDefendingPlayerEffect(3, false), "When {this} dies during combat, if it was attacking, ", false);
    }

    public InfestedTerranAbility(final InfestedTerranAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
            if(permanent != null && permanent.isAttacking() && game.getPhase().getType() == TurnPhase.COMBAT) {
                // TODO: If this doesn't trigger because the permanent is no long attacking, add a Watcher (see CathedralMembrane.java)
                return true;
            }
        }
        return false;
    }

    @Override
    public InfestedTerranAbility copy() {
        return new InfestedTerranAbility(this);
    }
}
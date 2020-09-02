
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author LevelX2
 */
public final class ZhurTaaDruid extends CardImpl {

    public ZhurTaaDruid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}{G}");
        this.subtype.add(SubType.HUMAN, SubType.DRUID);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());
        // Whenever you tap Zhur-Taa Druid for mana, it deals 1 damage to each opponent.
        this.addAbility(new ZhurTaaDruidAbility());

    }

    public ZhurTaaDruid(final ZhurTaaDruid card) {
        super(card);
    }

    @Override
    public ZhurTaaDruid copy() {
        return new ZhurTaaDruid(this);
    }
}

class ZhurTaaDruidAbility extends TriggeredAbilityImpl {

    public ZhurTaaDruidAbility() {
        super(Zone.BATTLEFIELD, new DamagePlayersEffect(1, TargetController.OPPONENT));
    }

    public ZhurTaaDruidAbility(final ZhurTaaDruidAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.TAPPED_FOR_MANA
                && !game.inCheckPlayableState();
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getSourceId().equals(getSourceId());
    }

    @Override
    public String getRule() {
        return "Whenever you tap {this} for mana, " + super.getRule();
    }

    @Override
    public ZhurTaaDruidAbility copy() {
        return new ZhurTaaDruidAbility(this);
    }

}

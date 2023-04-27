
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.OutlastAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.WarriorToken;
import mage.game.stack.StackAbility;

/**
 *
 * @author LevelX2
 */
public final class HeraldOfAnafenza extends CardImpl {

    public HeraldOfAnafenza(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Outlast {2}{W} <em>({2}{W} {T}: Put a +1/+1 counter on this creature.  Outlast only as a sorcery.)</em>
        this.addAbility(new OutlastAbility(new ManaCostsImpl<>("{2}{W}")));

        // Whenever you activate Herald of Anafenza's outlast ability, create a 1/1 white Warrior creature token.
        this.addAbility(new HeraldOfAnafenzaTriggeredAbility());

    }

    private HeraldOfAnafenza(final HeraldOfAnafenza card) {
        super(card);
    }

    @Override
    public HeraldOfAnafenza copy() {
        return new HeraldOfAnafenza(this);
    }
}

class HeraldOfAnafenzaTriggeredAbility extends TriggeredAbilityImpl {

    public HeraldOfAnafenzaTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new WarriorToken()), false);
        setTriggerPhrase("Whenever you activate {this}'s outlast ability, ");
    }

    public HeraldOfAnafenzaTriggeredAbility(final HeraldOfAnafenzaTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HeraldOfAnafenzaTriggeredAbility copy() {
        return new HeraldOfAnafenzaTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(getSourceId())) {
            StackAbility stackAbility = (StackAbility) game.getStack().getStackObject(event.getTargetId());
            if (stackAbility != null && (stackAbility.getStackAbility() instanceof OutlastAbility)) {
                return true;
            }
        }
        return false;
    }
}

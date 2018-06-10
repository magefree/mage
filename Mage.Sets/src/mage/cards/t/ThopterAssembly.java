
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.token.ThopterColorlessToken;

/**
 *
 * @author Loki
 */
public final class ThopterAssembly extends CardImpl {

    public ThopterAssembly(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{6}");
        this.subtype.add(SubType.THOPTER);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your upkeep, if you control no Thopters other than Thopter Assembly,
        // return Thopter Assembly to its owner's hand and create five 1/1 colorless Thopter artifact
        // creature tokens with flying.
        this.addAbility(new ThopterAssemblyTriggeredAbility());
    }

    public ThopterAssembly(final ThopterAssembly card) {
        super(card);
    }

    @Override
    public ThopterAssembly copy() {
        return new ThopterAssembly(this);
    }
}

class ThopterAssemblyTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterPermanent filter = new FilterPermanent();
    static {
        filter.add(new SubtypePredicate(SubType.THOPTER));
        filter.add(new AnotherPredicate());
    }

    ThopterAssemblyTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ReturnToHandSourceEffect(true));
        this.addEffect(new CreateTokenEffect(new ThopterColorlessToken(), 5));
    }

    ThopterAssemblyTriggeredAbility(final ThopterAssemblyTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ThopterAssemblyTriggeredAbility copy() {
        return new ThopterAssemblyTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.controllerId)) {
            if (game.getBattlefield().count(filter, this.getSourceId(), this.getControllerId(), game) == 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "At the beginning of your upkeep, if you control no Thopters other than {this}, return {this} to its owner's hand and create five 1/1 colorless Thopter artifact creature tokens with flying";
    }
}
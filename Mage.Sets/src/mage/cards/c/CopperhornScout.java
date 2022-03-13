
package mage.cards.c;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author maurer.it_at_gmail.com
 */
public final class CopperhornScout extends CardImpl {

    public CopperhornScout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SCOUT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(new CopperhornScoutTriggeredAbility());
    }

    private CopperhornScout(final CopperhornScout card) {
        super(card);
    }

    @Override
    public CopperhornScout copy() {
        return new CopperhornScout(this);
    }
}

class CopperhornScoutTriggeredAbility extends TriggeredAbilityImpl {

    public CopperhornScoutTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CopperhornScoutUntapEffect(), false);
    }

    public CopperhornScoutTriggeredAbility(final CopperhornScoutTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CopperhornScoutTriggeredAbility copy() {
        return new CopperhornScoutTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getSourceId().equals(this.getSourceId());
    }

    @Override
    public String getRule() {
        return "Whenever {this} attacks, untap each other creature you control.";
    }
}

class CopperhornScoutUntapEffect extends OneShotEffect {

    CopperhornScoutUntapEffect ( ) {
        super(Outcome.Untap);
    }

    CopperhornScoutUntapEffect ( CopperhornScoutUntapEffect effect ) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();

        List<Permanent> creatures = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game);

        for ( Permanent creature : creatures ) {
            if ( !creature.getId().equals(source.getSourceId()) ) {
                creature.untap(game);
            }
        }

        return true;
    }

    @Override
    public CopperhornScoutUntapEffect copy() {
        return new CopperhornScoutUntapEffect(this);
    }

}

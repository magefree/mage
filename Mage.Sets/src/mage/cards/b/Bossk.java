
package mage.cards.b;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.BountyAbility;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Styxo
 */
public final class Bossk extends CardImpl {

    public Bossk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TRANDOSHAN, SubType.HUNTER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever a land enter the battlefield under your control, if you control five or more lands, put a bounty counter on target creature an opponet controls
        this.addAbility(new BosskTriggeredAbility());

        // <i>Bounty</i> &mdash; Whenever a creature an opponent controls with a bounty counter on it dies, you may search your library for a basic land card, reveal it, and put it in to your hand. If you do, shuffle your library.
        this.addAbility(new BountyAbility(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true), true));
    }

    private Bossk(final Bossk card) {
        super(card);
    }

    @Override
    public Bossk copy() {
        return new Bossk(this);
    }
}

class BosskTriggeredAbility extends TriggeredAbilityImpl {

    BosskTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.BOUNTY.createInstance()));
        this.addTarget(new TargetOpponentsCreaturePermanent());
    }

    BosskTriggeredAbility(BosskTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        return game.getBattlefield().count(new FilterControlledLandPermanent(), getControllerId(), this, game) > 4;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null && permanent.isLand(game) && permanent.isControlledBy(this.getControllerId())) {
            return true;
        }
        return false;
    }

    @Override
    public BosskTriggeredAbility copy() {
        return new BosskTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a land enter the battlefield under your control, if you control five or more lands, put a bounty counter on target creature an opponet controls";
    }
}

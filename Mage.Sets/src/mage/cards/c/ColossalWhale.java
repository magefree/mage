
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.delayed.OnLeaveReturnExiledToBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.IslandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public final class ColossalWhale extends CardImpl {

    public ColossalWhale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{U}{U}");
        this.subtype.add(SubType.WHALE);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Islandwalk
        this.addAbility(new IslandwalkAbility());
        // Whenever Colossal Whale attacks, you may exile target creature defending player controls until Colossal Whale leaves the battlefield.
        this.addAbility(new ColossalWhaleAbility());

    }

    private ColossalWhale(final ColossalWhale card) {
        super(card);
    }

    @Override
    public ColossalWhale copy() {
        return new ColossalWhale(this);
    }
}

class ColossalWhaleAbility extends TriggeredAbilityImpl {

    public ColossalWhaleAbility() {
        super(Zone.BATTLEFIELD, null);
        this.addEffect(new ColossalWhaleExileEffect());
        this.addEffect(new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledToBattlefieldAbility()));
        setTriggerPhrase("Whenever {this} attacks, ");
    }

    public ColossalWhaleAbility(final ColossalWhaleAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.getSourceId())) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent("creature defending player controls");
            UUID defenderId = game.getCombat().getDefenderId(sourceId);
            filter.add(new ControllerIdPredicate(defenderId));

            this.getTargets().clear();
            TargetCreaturePermanent target = new TargetCreaturePermanent(filter);
            this.addTarget(target);
            return true;
        }
        return false;
    }

    @Override
    public ColossalWhaleAbility copy() {
        return new ColossalWhaleAbility(this);
    }
}

class ColossalWhaleExileEffect extends OneShotEffect {

    public ColossalWhaleExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may exile target creature defending player controls until {this} leaves the battlefield";
    }

    public ColossalWhaleExileEffect(final ColossalWhaleExileEffect effect) {
        super(effect);
    }

    @Override
    public ColossalWhaleExileEffect copy() {
        return new ColossalWhaleExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        // If Whale leaves the battlefield before its triggered ability resolves,
        // the target creature won't be exiled.
        if (permanent != null) {
            return new ExileTargetEffect(CardUtil.getCardExileZoneId(game, source), permanent.getIdName()).apply(game, source);
        }
        return false;
    }
}

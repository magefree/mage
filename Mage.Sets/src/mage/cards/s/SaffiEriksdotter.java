
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Plopman
 */
public final class SaffiEriksdotter extends CardImpl {

    public SaffiEriksdotter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Sacrifice Saffi Eriksdotter: When target creature is put into your graveyard from the battlefield this turn, return that card to the battlefield.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SaffiEriksdotterEffect(), new SacrificeSourceCost());
        Target target = new TargetCreaturePermanent(new FilterCreaturePermanent());
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private SaffiEriksdotter(final SaffiEriksdotter card) {
        super(card);
    }

    @Override
    public SaffiEriksdotter copy() {
        return new SaffiEriksdotter(this);
    }
}

class SaffiEriksdotterEffect extends OneShotEffect {

    public SaffiEriksdotterEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "When target creature is put into your graveyard this turn, return that card to the battlefield";
    }

    public SaffiEriksdotterEffect(final SaffiEriksdotterEffect effect) {
        super(effect);
    }

    @Override
    public SaffiEriksdotterEffect copy() {
        return new SaffiEriksdotterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        DelayedTriggeredAbility delayedAbility = new SaffiEriksdotterDelayedTriggeredAbility(new FixedTarget(this.getTargetPointer().getFirst(game, source)));
        game.addDelayedTriggeredAbility(delayedAbility, source);
        return true;
    }
}

class SaffiEriksdotterDelayedTriggeredAbility extends DelayedTriggeredAbility {

    protected FixedTarget fixedTarget;

    public SaffiEriksdotterDelayedTriggeredAbility(FixedTarget fixedTarget) {
        super(new ReturnToBattlefieldUnderYourControlTargetEffect(), Duration.EndOfTurn);
        this.getEffects().get(0).setTargetPointer(fixedTarget);
        this.fixedTarget = fixedTarget;
        setTriggerPhrase("When target creature is put into your graveyard from the battlefield this turn, ");
    }

    public SaffiEriksdotterDelayedTriggeredAbility(final SaffiEriksdotterDelayedTriggeredAbility ability) {
        super(ability);
        this.fixedTarget = ability.fixedTarget;
    }

    @Override
    public SaffiEriksdotterDelayedTriggeredAbility copy() {
        return new SaffiEriksdotterDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((ZoneChangeEvent) event).isDiesEvent()) {
            if (fixedTarget.getFirst(game, this).equals(event.getTargetId())) {
                if (this.isControlledBy(event.getPlayerId())) {
                    return true;
                }
            }
        }
        return false;
    }
}

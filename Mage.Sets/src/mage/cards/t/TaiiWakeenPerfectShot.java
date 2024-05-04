package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TaiiWakeenPerfectShot extends CardImpl {

    public TaiiWakeenPerfectShot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever a source you control deals noncombat damage to a creature equal to that creature's toughness, draw a card.
        this.addAbility(new TaiiWakeenPerfectShotTriggeredAbility());

        // {X}, {T}: If a source you control would deal noncombat damage to a permanent or player this turn, it deals that much damage plus X instead.
        Ability ability = new SimpleActivatedAbility(new TaiiWakeenPerfectShotEffect(), new ManaCostsImpl<>("{X}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private TaiiWakeenPerfectShot(final TaiiWakeenPerfectShot card) {
        super(card);
    }

    @Override
    public TaiiWakeenPerfectShot copy() {
        return new TaiiWakeenPerfectShot(this);
    }
}

class TaiiWakeenPerfectShotTriggeredAbility extends TriggeredAbilityImpl {

    TaiiWakeenPerfectShotTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1));
        setTriggerPhrase("Whenever a source you control deals noncombat damage to a creature equal to that creature's toughness, ");
    }

    private TaiiWakeenPerfectShotTriggeredAbility(final TaiiWakeenPerfectShotTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TaiiWakeenPerfectShotTriggeredAbility copy() {
        return new TaiiWakeenPerfectShotTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        return !((DamagedEvent) event).isCombatDamage()
                && isControlledBy(game.getControllerId(event.getSourceId()))
                && permanent != null
                && permanent.isCreature(game)
                && event.getAmount() == permanent.getToughness().getValue();
    }
}

class TaiiWakeenPerfectShotEffect extends ReplacementEffectImpl {

    TaiiWakeenPerfectShotEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "if a source you control would deal noncombat damage to " +
                "a permanent or player this turn, it deals that much damage plus X instead";
    }

    private TaiiWakeenPerfectShotEffect(final TaiiWakeenPerfectShotEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGE_PERMANENT:
            case DAMAGE_PLAYER:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return !((DamageEvent) event).isCombatDamage()
                && source.isControlledBy(game.getControllerId(event.getSourceId()));
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowInc(event.getAmount(), source.getManaCostsToPay().getX()));
        return false;
    }

    @Override
    public TaiiWakeenPerfectShotEffect copy() {
        return new TaiiWakeenPerfectShotEffect(this);
    }
}

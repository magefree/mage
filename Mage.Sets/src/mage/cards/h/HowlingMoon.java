package mage.cards.h;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.WolfToken;
import mage.target.common.TargetControlledPermanent;
import mage.watchers.common.CastSpellLastTurnWatcher;

/**
 *
 * @author weirddan455
 */
public final class HowlingMoon extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Wolf or Werewolf you control");

    static {
        filter.add(Predicates.or(SubType.WOLF.getPredicate(), SubType.WEREWOLF.getPredicate()));
    }

    public HowlingMoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // At the beginning of combat on your turn, target Wolf or Werewolf you control gets +2/+2 until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new BoostTargetEffect(2, 2, Duration.EndOfTurn),
                TargetController.YOU, false
        );
        ability.addTarget(new TargetControlledPermanent(filter));
        this.addAbility(ability);

        // Whenever an opponent casts their second spell each turn, create a 2/2 green Wolf creature token.
        this.addAbility(new HowlingMoonTriggeredAbility(), new CastSpellLastTurnWatcher());
    }

    private HowlingMoon(final HowlingMoon card) {
        super(card);
    }

    @Override
    public HowlingMoon copy() {
        return new HowlingMoon(this);
    }
}

class HowlingMoonTriggeredAbility extends TriggeredAbilityImpl {

    public HowlingMoonTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new WolfToken()));
    }

    private HowlingMoonTriggeredAbility(final HowlingMoonTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HowlingMoonTriggeredAbility copy() {
        return new HowlingMoonTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(controllerId).contains(event.getPlayerId())) {
            CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
            return watcher != null && watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(event.getPlayerId()) == 2;
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever an opponent casts their second spell each turn, ";
    }
}

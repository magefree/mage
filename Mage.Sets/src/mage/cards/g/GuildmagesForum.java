package mage.cards.g;

import java.util.UUID;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

/**
 *
 * @author LevelX2
 */
public final class GuildmagesForum extends CardImpl {

    public GuildmagesForum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {1}, {T}: Add one mana of any color. If that mana is spent on a multicolored creature spell, that creature enters the battlefield with an additional +1/+1 counter on it.
        Ability ability = new AnyColorManaAbility(new GenericManaCost(1), true);
        ability.getEffects().get(0).setText("Add one mana of any color. If that mana is spent on a multicolored creature spell, that creature enters the battlefield with an additional +1/+1 counter on it");
        ability.addCost(new TapSourceCost());
        this.addAbility(ability, new GuildmagesForumWatcher(ability.getId()));
    }

    private GuildmagesForum(final GuildmagesForum card) {
        super(card);
    }

    @Override
    public GuildmagesForum copy() {
        return new GuildmagesForum(this);
    }
}

class GuildmagesForumWatcher extends Watcher {

    private final UUID sourceAbilityID;

    GuildmagesForumWatcher(UUID sourceAbilityID) {
        super(WatcherScope.CARD);
        this.sourceAbilityID = sourceAbilityID;
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.MANA_PAID) {
            MageObject target = game.getObject(event.getTargetId());
            if (event.getSourceId() != null
                    && event.getSourceId().equals(this.getSourceId())
                    && target != null && target.isCreature(game) && target.getColor(game).isMulticolored()
                    && event.getFlag()) {
                if (target instanceof Spell) {
                    game.getState().addEffect(new GuildmagesForumEntersBattlefieldEffect(
                            new MageObjectReference(((Spell) target).getSourceId(), target.getZoneChangeCounter(game), game)),
                        game.getAbility(sourceAbilityID, this.getSourceId()).orElse(null)); //null will cause an immediate crash
                }
            }
        }
    }

}

class GuildmagesForumEntersBattlefieldEffect extends ReplacementEffectImpl {

    private final MageObjectReference mor;

    public GuildmagesForumEntersBattlefieldEffect(MageObjectReference mor) {
        super(Duration.EndOfTurn, Outcome.BoostCreature);
        this.staticText = "If that mana is spent on a multicolored creature spell, that creature enters the battlefield with an additional +1/+1 counter on it";
        this.mor = mor;
    }

    private GuildmagesForumEntersBattlefieldEffect(final GuildmagesForumEntersBattlefieldEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        return permanent != null && mor.refersTo(permanent, game);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent target = ((EntersTheBattlefieldEvent) event).getTarget();
        if (target != null) {
            target.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game, event.getAppliedEffects());
        }
        return false;
    }

    @Override
    public GuildmagesForumEntersBattlefieldEffect copy() {
        return new GuildmagesForumEntersBattlefieldEffect(this);
    }
}

package mage.cards.w;

import mage.MageIdentifier;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.counter.AddCounterEnteringCreatureEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.watchers.Watcher;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class WorldheartPhoenix extends CardImpl {

    public WorldheartPhoenix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.PHOENIX);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // You may cast Worldheart Phoenix from your graveyard by paying {W}{U}{B}{R}{G} rather than paying its mana cost.
        // If you do, it enters the battlefield with two +1/+1 counters on it.
        Ability ability = new SimpleStaticAbility(Zone.ALL, new WorldheartPhoenixPlayEffect())
                .setIdentifier(MageIdentifier.WorldheartPhoenixAlternateCast);
        ability.addWatcher(new WorldheartPhoenixWatcher());
        this.addAbility(ability);

    }

    private WorldheartPhoenix(final WorldheartPhoenix card) {
        super(card);
    }

    @Override
    public WorldheartPhoenix copy() {
        return new WorldheartPhoenix(this);
    }
}

class WorldheartPhoenixPlayEffect extends AsThoughEffectImpl {

    WorldheartPhoenixPlayEffect() {
        super(AsThoughEffectType.CAST_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfGame, Outcome.Benefit);
        staticText = "You may cast {this} from your graveyard by paying {W}{U}{B}{R}{G} rather than paying its mana cost. " +
                "If you do, it enters the battlefield with two +1/+1 counters on it";
    }

    private WorldheartPhoenixPlayEffect(final WorldheartPhoenixPlayEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public WorldheartPhoenixPlayEffect copy() {
        return new WorldheartPhoenixPlayEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (sourceId.equals(source.getSourceId()) && source.isControlledBy(affectedControllerId)) {
            if (game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD) {
                Player player = game.getPlayer(affectedControllerId);
                if (player != null) {
                    player.setCastSourceIdWithAlternateMana(
                            sourceId, new ManaCostsImpl<>("{W}{U}{B}{R}{G}"), null,
                            MageIdentifier.WorldheartPhoenixAlternateCast
                    );
                    return true;
                }
            }
        }
        return false;
    }

}

class WorldheartPhoenixWatcher extends Watcher {

    WorldheartPhoenixWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (GameEvent.EventType.SPELL_CAST.equals(event.getType())
                && event.hasApprovingIdentifier(MageIdentifier.WorldheartPhoenixAlternateCast)) {
            Spell target = game.getSpell(event.getTargetId());
            if (target != null) {
                game.getState().addEffect(new AddCounterEnteringCreatureEffect(new MageObjectReference(target.getCard(), game),
                                CounterType.P1P1.createInstance(2), Outcome.BoostCreature),
                        target.getSpellAbility());
            }
        }
    }
}

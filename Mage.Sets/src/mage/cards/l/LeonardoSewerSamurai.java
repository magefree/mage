package mage.cards.l;

import mage.MageIdentifier;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.counter.AddCounterEnteringCreatureEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.SneakAbility;
import mage.cards.Card;
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
 * @author TheElk801
 */
public final class LeonardoSewerSamurai extends CardImpl {

    public LeonardoSewerSamurai(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.TURTLE);
        this.subtype.add(SubType.SAMURAI);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Sneak {2}{W}{W}
        this.addAbility(new SneakAbility(this, "{2}{W}{W}"));

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // During your turn, you may cast creature spells with power or toughness 1 or less from your graveyard. If you cast a spell this way, that creature enters with a finality counter on it.
        this.addAbility(new SimpleStaticAbility(new LeonardoSewerSamuraiEffect())
                .setIdentifier(MageIdentifier.LeonardoSewerSamuraiAlternateCast), new LeonardoSewerSamuraiWatcher());
    }

    private LeonardoSewerSamurai(final LeonardoSewerSamurai card) {
        super(card);
    }

    @Override
    public LeonardoSewerSamurai copy() {
        return new LeonardoSewerSamurai(this);
    }
}

class LeonardoSewerSamuraiEffect extends AsThoughEffectImpl {

    LeonardoSewerSamuraiEffect() {
        super(AsThoughEffectType.CAST_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.AIDontUseIt);
        staticText = "during your turn, you may cast creature spells with power or toughness 1 or less " +
                "from your graveyard. If you cast a spell this way, that creature enters with a finality counter on it";
    }

    private LeonardoSewerSamuraiEffect(final LeonardoSewerSamuraiEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public LeonardoSewerSamuraiEffect copy() {
        return new LeonardoSewerSamuraiEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (!source.isControlledBy(affectedControllerId)) {
            return false;
        }
        Card card = game.getCard(objectId);
        Player player = game.getPlayer(affectedControllerId);
        return card != null
                && player != null
                && card.isOwnedBy(affectedControllerId)
                && game.getState().getZone(objectId).match(Zone.GRAVEYARD)
                && card.isCreature(game)
                && (card.getPower().getValue() <= 1 || card.getToughness().getValue() <= 1);
    }
}

class LeonardoSewerSamuraiWatcher extends Watcher {

    LeonardoSewerSamuraiWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (!GameEvent.EventType.SPELL_CAST.equals(event.getType())
                || !event.hasApprovingIdentifier(MageIdentifier.LeonardoSewerSamuraiAlternateCast)) {
            return;
        }
        Spell target = game.getSpell(event.getTargetId());
        if (target != null) {
            game.getState().addEffect(new AddCounterEnteringCreatureEffect(
                    new MageObjectReference(target.getCard(), game),
                    CounterType.FINALITY.createInstance(), Outcome.UnboostCreature
            ), target.getSpellAbility());
        }
    }
}

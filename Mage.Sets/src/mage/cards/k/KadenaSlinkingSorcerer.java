package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.WatcherScope;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.ObjectPlayer;
import mage.filter.predicate.ObjectPlayerPredicate;
import mage.filter.predicate.other.FaceDownPredicate;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KadenaSlinkingSorcerer extends CardImpl {

    private static final FilterCard filter = new FilterCard();
    private static final FilterPermanent filter2 = new FilterControlledCreaturePermanent("a face-down creature");

    static {
        filter.add(KadenaSlinkingSorcererPredicate.instance);
        filter2.add(FaceDownPredicate.instance);
    }

    public KadenaSlinkingSorcerer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.NAGA);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // The first face-down creature spell you cast each turn costs {3} less to cast.
        this.addAbility(new SimpleStaticAbility(
                new SpellsCostReductionControllerEffect(filter, 3)
                        .setText("The first face-down creature spell you cast each turn costs {3} less to cast.")
        ), new KadenaSlinkingSorcererWatcher());

        // Whenever a face-down creature enters the battlefield under your control, draw a card.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new DrawCardSourceControllerEffect(1), filter2
        ));
    }

    private KadenaSlinkingSorcerer(final KadenaSlinkingSorcerer card) {
        super(card);
    }

    @Override
    public KadenaSlinkingSorcerer copy() {
        return new KadenaSlinkingSorcerer(this);
    }
}

enum KadenaSlinkingSorcererPredicate implements ObjectPlayerPredicate<ObjectPlayer<Controllable>> {
    instance;

    @Override
    public boolean apply(ObjectPlayer<Controllable> input, Game game) {
        if (input.getObject() instanceof Spell
                && ((Spell) input.getObject()).isCreature()
                && ((Spell) input.getObject()).isFaceDown(game)) {
            KadenaSlinkingSorcererWatcher watcher = game.getState().getWatcher(KadenaSlinkingSorcererWatcher.class);
            return watcher != null && !watcher.castFaceDownThisTurn(input.getPlayerId());
        }
        return false;
    }
}

class KadenaSlinkingSorcererWatcher extends Watcher {


    private final Set<UUID> castFaceDown;

    KadenaSlinkingSorcererWatcher() {
        super(WatcherScope.GAME);
        castFaceDown = new HashSet<>();
    }

    private KadenaSlinkingSorcererWatcher(final KadenaSlinkingSorcererWatcher watcher) {
        super(watcher);
        this.castFaceDown = new HashSet<>();
        castFaceDown.addAll(watcher.castFaceDown);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST) {
            return;
        }
        Spell spell = (Spell) game.getObject(event.getTargetId());
        if (spell == null || !spell.isCreature() || !spell.isFaceDown(game)) {
            return;
        }
        castFaceDown.add(event.getPlayerId());
    }

    boolean castFaceDownThisTurn(UUID playerId) {
        return castFaceDown.contains(playerId);
    }

    @Override
    public KadenaSlinkingSorcererWatcher copy() {
        return new KadenaSlinkingSorcererWatcher(this);
    }

    @Override
    public void reset() {
        super.reset();
        castFaceDown.clear();
    }

}

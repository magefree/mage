package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.WatcherScope;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.card.FaceDownCastablePredicate;
import mage.filter.predicate.card.FaceDownPredicate;
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

    private static final FilterCard filterFirstFaceDownSpell = new FilterCard("first face-down creature spell");
    private static final FilterPermanent filterFaceDownPermanent = new FilterControlledCreaturePermanent("a face-down creature");

    static {
        filterFirstFaceDownSpell.add(KadenaSlinkingSorcererPredicate.instance);
        filterFaceDownPermanent.add(FaceDownPredicate.instance);
    }

    public KadenaSlinkingSorcerer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.NAGA);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // The first face-down creature spell you cast each turn costs {3} less to cast.
        this.addAbility(new SimpleStaticAbility(
                new SpellsCostReductionControllerEffect(filterFirstFaceDownSpell, 3)
                        .setText("The first face-down creature spell you cast each turn costs {3} less to cast.")
        ), new KadenaSlinkingSorcererWatcher());

        // Whenever a face-down creature enters the battlefield under your control, draw a card.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new DrawCardSourceControllerEffect(1), filterFaceDownPermanent
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

enum KadenaSlinkingSorcererPredicate implements Predicate<Card> {
    instance;

    @Override
    public boolean apply(Card input, Game game) {
        KadenaSlinkingSorcererWatcher watcher = game.getState().getWatcher(KadenaSlinkingSorcererWatcher.class);
        if (watcher != null) {
            return FaceDownCastablePredicate.instance.apply(input, game)
                    && !watcher.castFaceDownThisTurn(input.getOwnerId());
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

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST) {
            return;
        }
        Spell spell = (Spell) game.getObject(event.getTargetId());
        if (spell == null || !spell.isCreature(game) || !spell.isFaceDown(game)) {
            return;
        }
        castFaceDown.add(event.getPlayerId());
    }

    boolean castFaceDownThisTurn(UUID playerId) {
        return castFaceDown.contains(playerId);
    }


    @Override
    public void reset() {
        super.reset();
        castFaceDown.clear();
    }

}

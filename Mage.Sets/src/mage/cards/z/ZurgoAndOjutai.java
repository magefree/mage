package mage.cards.z;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceEnteredThisTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.events.DamagedBatchEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTargets;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class ZurgoAndOjutai extends CardImpl {

    public ZurgoAndOjutai(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Zurgo and Ojutai has hexproof as long as it entered the battlefield this turn.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(HexproofAbility.getInstance(), Duration.WhileOnBattlefield),
                SourceEnteredThisTurnCondition.instance, "{this} has hexproof as long as it entered the battlefield this turn"
        )));

        // Whenever one or more Dragons you control deal combat damage to a player or battle, look at the top three cards of your library. Put one of them into your hand and the rest on the bottom of your library in any order. You may return one of those Dragons to its owner's hand.
        this.addAbility(new ZurgoAndOjutaiTriggeredAbility());
    }

    private ZurgoAndOjutai(final ZurgoAndOjutai card) {
        super(card);
    }

    @Override
    public ZurgoAndOjutai copy() {
        return new ZurgoAndOjutai(this);
    }
}

class ZurgoAndOjutaiTriggeredAbility extends TriggeredAbilityImpl {

    ZurgoAndOjutaiTriggeredAbility() {
        super(Zone.BATTLEFIELD, new LookLibraryAndPickControllerEffect(3, 1, PutCards.HAND, PutCards.BOTTOM_ANY));
        this.addEffect(new ZurgoAndOjutaiEffect());
        this.setTriggerPhrase("Whenever one or more Dragons you control deal combat damage to a player or battle, ");
    }

    private ZurgoAndOjutaiTriggeredAbility(final ZurgoAndOjutaiTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ZurgoAndOjutaiTriggeredAbility copy() {
        return new ZurgoAndOjutaiTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER_BATCH
                || event.getType() == GameEvent.EventType.DAMAGED_PERMANENT_BATCH;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedBatchEvent dEvent = (DamagedBatchEvent) event;
        List<Permanent> permanents = dEvent
                .getEvents()
                .stream()
                .map(e -> {
                    Permanent permanent = game.getPermanent(e.getSourceId());
                    Permanent defender = game.getPermanent(e.getTargetId());
                    if (permanent != null
                            && permanent.hasSubtype(SubType.DRAGON, game)
                            && permanent.isControlledBy(this.getControllerId())
                            && ((defender != null && defender.isBattle(game))
                            || game.getPlayer(e.getTargetId()) != null)) {
                        return permanent;
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (permanents.isEmpty()) {
            return false;
        }
        this.getEffects().setTargetPointer(new FixedTargets(permanents, game));
        return true;
    }
}

class ZurgoAndOjutaiEffect extends OneShotEffect {

    ZurgoAndOjutaiEffect() {
        super(Outcome.Benefit);
        staticText = "You may return one of those Dragons to its owner's hand";
    }

    private ZurgoAndOjutaiEffect(final ZurgoAndOjutaiEffect effect) {
        super(effect);
    }

    @Override
    public ZurgoAndOjutaiEffect copy() {
        return new ZurgoAndOjutaiEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        List<Predicate<Permanent>> predicates = this
                .getTargetPointer()
                .getTargets(game, source)
                .stream()
                .map(PermanentIdPredicate::new)
                .collect(Collectors.toList());
        if (predicates.isEmpty()) {
            return false;
        }
        FilterPermanent filter = new FilterPermanent("Dragon");
        filter.add(Predicates.or(predicates));
        TargetPermanent target = new TargetPermanent(0, 1, filter, true);
        target.withChooseHint("return to hand");
        player.choose(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        return permanent != null && player.moveCards(permanent, Zone.HAND, source, game);
    }
}

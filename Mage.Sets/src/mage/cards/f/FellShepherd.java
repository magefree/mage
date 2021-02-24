package mage.cards.f;

import mage.MageInt;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class FellShepherd extends CardImpl {

    public FellShepherd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(8);
        this.toughness = new MageInt(6);

        // Whenever Fell Shepherd deals combat damage to a player, you may return to your hand all creature cards that were put into your graveyard from the battlefield this turn.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new FellShepherdEffect(), true), new FellShepherdWatcher());

        // {B}, Sacrifice another creature: Target creature gets -2/-2 until end of turn.
        Ability ability = new SimpleActivatedAbility(new BoostTargetEffect(-2, -2, Duration.EndOfTurn), new ManaCostsImpl("{B}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE)));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

    }

    private FellShepherd(final FellShepherd card) {
        super(card);
    }

    @Override
    public FellShepherd copy() {
        return new FellShepherd(this);
    }
}

class FellShepherdEffect extends OneShotEffect {

    FellShepherdEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "return to your hand all creature cards that were put into your graveyard from the battlefield this turn";
    }

    private FellShepherdEffect(final FellShepherdEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        FellShepherdWatcher watcher = game.getState().getWatcher(FellShepherdWatcher.class);
        if (player == null || watcher == null) {
            return false;
        }
        return player.moveCards(watcher.getCards(source.getControllerId(), game), Zone.HAND, source, game);
    }

    @Override
    public FellShepherdEffect copy() {
        return new FellShepherdEffect(this);
    }
}

class FellShepherdWatcher extends Watcher {

    private final Set<MageObjectReference> morMap = new HashSet<>();

    FellShepherdWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && ((ZoneChangeEvent) event).isDiesEvent()) {
            morMap.add(new MageObjectReference(((ZoneChangeEvent) event).getTarget(), game, 1));
        }
    }

    Cards getCards(UUID ownerId, Game game) {
        Cards cards = new CardsImpl();
        morMap.stream()
                .map(m -> m.getCard(game))
                .filter(Objects::nonNull)
                .filter(MageObject::isCreature)
                .filter(c -> c.isOwnedBy(ownerId))
                .forEach(cards::add);
        return cards;
    }

    @Override
    public void reset() {
        super.reset();
        morMap.clear();
    }
}

package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.common.ExileTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.token.MechtitanToken;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MechtitanCore extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledArtifactPermanent("other artifact creatures and/or Vehicles you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
    }

    public MechtitanCore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // {5}, Exile Mechtitan Core and four other artifact creatures and/or Vehicles you control: Create Mechtitan, a legendary 10/10 Construct artifact creature token with flying, vigilance, trample, lifelink, and haste that's all colors. When that token leaves the battlefield, return all cards exiled with Mechtitan Core except Mechtitan Core to the battlefield tapped under their owners' control.
        Ability ability = new SimpleActivatedAbility(new MechtitanCoreTokenEffect(), new GenericManaCost(5));
        ability.addCost(new CompositeCost(
                new ExileSourceCost(), new ExileTargetCost(new TargetControlledPermanent(4, 4, filter, true)),
                "exile {this} and four other artifact creatures and/or Vehicles you control"));
        this.addAbility(ability);

        // Crew 2
        this.addAbility(new CrewAbility(2));
    }

    private MechtitanCore(final MechtitanCore card) {
        super(card);
    }

    @Override
    public MechtitanCore copy() {
        return new MechtitanCore(this);
    }
}

class MechtitanCoreTokenEffect extends OneShotEffect {

    MechtitanCoreTokenEffect() {
        super(Outcome.Benefit);
        staticText = "create Mechtitan, a legendary 10/10 Construct artifact creature token with flying, "
                + "vigilance, trample, lifelink, and haste that's all colors. "
                + "When that token leaves the battlefield, return all cards exiled with {this} except "
                + "{this} to the battlefield tapped under their owners' control";
    }

    private MechtitanCoreTokenEffect(final MechtitanCoreTokenEffect effect) {
        super(effect);
    }

    @Override
    public MechtitanCoreTokenEffect copy() {
        return new MechtitanCoreTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new MechtitanToken();
        token.putOntoBattlefield(1, game, source);
        game.addDelayedTriggeredAbility(new MechtitanCoreTriggeredAbility(token, source, game), source);
        return true;
    }
}

class MechtitanCoreTriggeredAbility extends DelayedTriggeredAbility {

    private final Set<UUID> tokenIds = new HashSet<>();

    MechtitanCoreTriggeredAbility(Token token, Ability source, Game game) {
        super(new MechtitanCoreReturnEffect(), Duration.Custom, false, false);
        this.getEffects().setTargetPointer(new FixedTargets(game.getExile().getExileZone(CardUtil.getExileZoneId(game, source)), game));
        tokenIds.addAll(token.getLastAddedTokenIds());
    }

    private MechtitanCoreTriggeredAbility(final MechtitanCoreTriggeredAbility ability) {
        super(ability);
        this.tokenIds.addAll(ability.tokenIds);
    }

    @Override
    public MechtitanCoreTriggeredAbility copy() {
        return new MechtitanCoreTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (tokenIds.contains(event.getTargetId()) && ((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD) {
            tokenIds.remove(event.getTargetId());
            return true;
        }
        return false;
    }

    @Override
    public boolean isInactive(Game game) {
        return tokenIds.isEmpty();
    }

    @Override
    public String getRule() {
        return "When that token leaves the battlefield, return all cards exiled with {this} except "
                + "{this} to the battlefield tapped under their owners' control";
    }
}

class MechtitanCoreReturnEffect extends OneShotEffect {

    MechtitanCoreReturnEffect() {
        super(Outcome.Benefit);
    }

    private MechtitanCoreReturnEffect(final MechtitanCoreReturnEffect effect) {
        super(effect);
    }

    @Override
    public MechtitanCoreReturnEffect copy() {
        return new MechtitanCoreReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(getTargetPointer().getTargets(game, source));
        return player.moveCards(
                cards.getCards(game), Zone.BATTLEFIELD, source, game,
                true, false, true, null
        );
    }
}
// and I'll form the head!

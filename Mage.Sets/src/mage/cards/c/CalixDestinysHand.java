package mage.cards.c;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.LoyaltyAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledEnchantmentPermanent;
import mage.filter.common.FilterEnchantmentCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;
import java.util.stream.Collectors;

import static mage.constants.Outcome.Benefit;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public final class CalixDestinysHand extends CardImpl {

    private static final FilterCard filter = new FilterEnchantmentCard();
    private static final FilterPermanent filter2
            = new FilterPermanent("creature or enchantment you don't control");

    static {
        filter2.add(TargetController.NOT_YOU.getControllerPredicate());
        filter2.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()
        ));
    }

    private static final FilterPermanent filter3 = new FilterControlledEnchantmentPermanent();

    public CalixDestinysHand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{G}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.CALIX);
        this.setStartingLoyalty(4);

        // +1: Look at the top four cards of your library. You may reveal an enchantment card from among them and put that card into your hand.
        // Put the rest on the bottom of your library in a random order.
        this.addAbility(new LoyaltyAbility(new LookLibraryAndPickControllerEffect(
                4, 1, filter, PutCards.HAND, PutCards.BOTTOM_RANDOM
        ).setText("Look at the top four cards of your library. "
                + "You may reveal an enchantment card from among them and put that card into your hand. "
                + "Put the rest on the bottom of your library in a random order."), 1
        ));

        // −3: Exile target creature or enchantment you don't control until target enchantment you control leaves the battlefield.
        Ability ability = new LoyaltyAbility(new CalixDestinysHandExileEffect(), -3);
        ability.addTarget(new TargetPermanent(filter2));
        ability.addTarget(new TargetPermanent(filter3));
        this.addAbility(ability);

        // −7: Return all enchantment cards from your graveyard to the battlefield.
        this.addAbility(new LoyaltyAbility(new CalixDestinysHandReturnEffect(), -7));
    }

    private CalixDestinysHand(final CalixDestinysHand card) {
        super(card);
    }

    @Override
    public CalixDestinysHand copy() {
        return new CalixDestinysHand(this);
    }
}

class CalixDestinysHandExileEffect extends OneShotEffect {

    CalixDestinysHandExileEffect() {
        super(Benefit);
        staticText = "Exile target creature or enchantment you don't control "
                + "until target enchantment you control leaves the battlefield.";
    }

    private CalixDestinysHandExileEffect(final CalixDestinysHandExileEffect effect) {
        super(effect);
    }

    @Override
    public CalixDestinysHandExileEffect copy() {
        return new CalixDestinysHandExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (source.getTargets().size() > 2) {
            return false;
        }
        source.getTargets();
        Permanent theirPerm = game.getPermanent(source.getTargets().get(0).getFirstTarget());
        Permanent myPerm = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (controller == null 
                || theirPerm == null 
                || myPerm == null) {
            return false;
        }
        MageObjectReference theirMor = new MageObjectReference(
                theirPerm.getId(), theirPerm.getZoneChangeCounter(game) + 1, game
        );
        MageObjectReference myMor = new MageObjectReference(myPerm, game);
        UUID exileId = CardUtil.getExileZoneId(myPerm.toString(), game);
        controller.moveCardsToExile(theirPerm, source, game, true, exileId, myPerm.getIdName());
        game.addDelayedTriggeredAbility(new CalixDestinysHandDelayedTriggeredAbility(theirMor, myMor), source);
        return true;
    }
}

class CalixDestinysHandDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private final MageObjectReference theirMor;
    private final MageObjectReference myMor;

    CalixDestinysHandDelayedTriggeredAbility(MageObjectReference theirMor, MageObjectReference myMor) {
        super(null, Duration.Custom, true, false);
        this.theirMor = theirMor;
        this.myMor = myMor;
        this.usesStack = false;
    }

    private CalixDestinysHandDelayedTriggeredAbility(final CalixDestinysHandDelayedTriggeredAbility ability) {
        super(ability);
        this.theirMor = ability.theirMor;
        this.myMor = ability.myMor;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getFromZone() != Zone.BATTLEFIELD
                || !this.myMor.refersTo(zEvent.getTarget(), game)) {
            return false;
        }
        this.getEffects().clear();
        this.addEffect(new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, false)
                .setTargetPointer(new FixedTarget(this.theirMor)));
        return true;
    }

    @Override
    public CalixDestinysHandDelayedTriggeredAbility copy() {
        return new CalixDestinysHandDelayedTriggeredAbility(this);
    }
}

class CalixDestinysHandReturnEffect extends OneShotEffect {

    CalixDestinysHandReturnEffect() {
        super(Benefit);
        staticText = "return all enchantment cards from your graveyard to the battlefield";
    }

    private CalixDestinysHandReturnEffect(final CalixDestinysHandReturnEffect effect) {
        super(effect);
    }

    @Override
    public CalixDestinysHandReturnEffect copy() {
        return new CalixDestinysHandReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        return player.moveCards(
                player.getGraveyard()
                        .getCards(game)
                        .stream()
                        .filter(card -> card.isEnchantment(game))
                        .collect(Collectors.toSet()),
                Zone.BATTLEFIELD, source, game
        );
    }
}

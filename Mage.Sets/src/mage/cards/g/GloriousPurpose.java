package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.PlanCounterThresholdTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public final class GloriousPurpose extends CardImpl {

    public GloriousPurpose(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        this.subtype.add(SubType.PLAN);
        this.addAbility(new GloriousPurposeConniveTriggeredAbility());

        ReflexiveTriggeredAbility reflexive = new ReflexiveTriggeredAbility(
                new GloriousPurposeFinishEffect(), false,
                "exile the top four cards of your library. You may cast any number of spells from among them without "
                        + "paying their mana costs. Put the rest into your hand"
        );
        this.addAbility(new PlanCounterThresholdTriggeredAbility(6, reflexive));
    }

    private GloriousPurpose(final GloriousPurpose card) {
        super(card);
    }

    @Override
    public GloriousPurpose copy() {
        return new GloriousPurpose(this);
    }
}

class GloriousPurposeConniveTriggeredAbility extends TriggeredAbilityImpl {

    GloriousPurposeConniveTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.P1P1.createInstance()), false);
        addEffect(new AddCountersSourceEffect(CounterType.PLAN.createInstance()));
        setTriggerPhrase("Whenever a creature you control connives, put a +1/+1 counter on that creature and a plan counter on {this}");
    }

    private GloriousPurposeConniveTriggeredAbility(final GloriousPurposeConniveTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CONNIVE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (permanent == null || !permanent.isCreature(game) || !permanent.isControlledBy(getControllerId())) {
            return false;
        }
        getEffects().setTargetPointer(new FixedTarget(permanent.getId(), game));
        return true;
    }

    @Override
    public GloriousPurposeConniveTriggeredAbility copy() {
        return new GloriousPurposeConniveTriggeredAbility(this);
    }
}

class GloriousPurposeFinishEffect extends OneShotEffect {

    GloriousPurposeFinishEffect() {
        super(Outcome.PlayForFree);
        staticText = "exile the top four cards of your library. You may cast any number of spells from among them "
                + "without paying their mana costs. Put the rest into your hand";
    }

    private GloriousPurposeFinishEffect(final GloriousPurposeFinishEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        Set<Card> topCards = controller.getLibrary().getTopCards(game, 4);
        if (topCards.isEmpty()) {
            return true;
        }

        List<UUID> exiledCardIds = new ArrayList<>();
        Cards spells = new CardsImpl();
        for (Card card : topCards) {
            if (controller.moveCards(card, Zone.EXILED, source, game)) {
                exiledCardIds.add(card.getId());
                if (!card.isLand(game)) {
                    spells.add(card);
                }
            }
        }

        CardUtil.castMultipleWithAttributeForFree(controller, source, game, spells, StaticFilters.FILTER_CARD_NON_LAND);

        Cards remaining = new CardsImpl();
        for (UUID cardId : exiledCardIds) {
            Card card = game.getCard(cardId);
            if (card != null && game.getState().getZone(card.getId()) == Zone.EXILED) {
                remaining.add(card);
            }
        }
        if (!remaining.isEmpty()) {
            controller.moveCards(remaining.getCards(game), Zone.HAND, source, game);
        }
        return true;
    }

    @Override
    public GloriousPurposeFinishEffect copy() {
        return new GloriousPurposeFinishEffect(this);
    }
}

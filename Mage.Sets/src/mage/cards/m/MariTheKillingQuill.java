package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.RemoveCounterCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.CastFromZonePredicate;
import mage.filter.predicate.card.FaceDownPredicate;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Alex-Vasile
 */
public class MariTheKillingQuill extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Assassins, Mercenaries, and Rogues");
    static {
        filter.add(Predicates.or(
                SubType.ASSASSIN.getPredicate(),
                SubType.MERCENARY.getPredicate(),
                SubType.ROGUE.getPredicate()
        ));
    }

    public MariTheKillingQuill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.addSubType(SubType.VAMPIRE, SubType.ASSASSIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever a creature an opponent controls dies, exile it with a hit counter on it.
        this.addAbility(new MariTheKillingQuillCreatureDiesAbility());

        // Assassins, Mercenaries, and Rogues you control have deathtouch and
        //  "Whenever this creature deals combat damage to a player, you may remove a hit counter from a card that player owns in exile.
        //   If you do, draw a card and create two Treasure tokens."
        GainAbilityControlledEffect gainDeathTouchEffect = new GainAbilityControlledEffect(DeathtouchAbility.getInstance(), Duration.WhileOnBattlefield, filter);
        Ability mainAbility = new SimpleStaticAbility(Zone.BATTLEFIELD, gainDeathTouchEffect);

        // NOTE: Optional part is handled inside the effect
        Ability dealsDamageAbility = new DealsCombatDamageToAPlayerTriggeredAbility(new MariTheKillingQuillDealsDamageEffect(), false, true);
        Effect drawAndTreasureEffect = new GainAbilityControlledEffect(dealsDamageAbility, Duration.WhileOnBattlefield, filter);
        drawAndTreasureEffect.setText(
                "\"Whenever this creature deals combat damage to a player, you may remove a hit counter from a card that player owns in exile. " +
                "If you do, draw a card and create two Treasure tokens.\"");
        drawAndTreasureEffect.concatBy("and");

        mainAbility.addEffect(drawAndTreasureEffect);

        this.addAbility(mainAbility);
    }

    private MariTheKillingQuill(final MariTheKillingQuill card) {
        super(card);
    }

    @Override
    public MariTheKillingQuill copy() {
        return new MariTheKillingQuill(this);
    }
}

class MariTheKillingQuillDealsDamageEffect extends OneShotEffect {

    MariTheKillingQuillDealsDamageEffect() {
        super(Outcome.Benefit);
    }

    private MariTheKillingQuillDealsDamageEffect(final MariTheKillingQuillDealsDamageEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || opponent == null) {
            return false;
        }

        FilterCard filterCard = new FilterCard("a card that player owns in exile");
        filterCard.add(new OwnerIdPredicate(opponent.getId()));
        filterCard.add(Predicates.not(FaceDownPredicate.instance));
        filterCard.add(new CastFromZonePredicate(Zone.EXILED));

        Effect doIfCostPaidEffect =  new DoIfCostPaid(
                new MariTheKillingQuillDrawAndTokenEffect(),
                new RemoveCounterCost(new TargetCard(Zone.EXILED, filterCard))
        );

        return doIfCostPaidEffect.apply(game, source);
    }

    @Override
    public MariTheKillingQuillDealsDamageEffect copy() {
        return new MariTheKillingQuillDealsDamageEffect(this);
    }
}

class MariTheKillingQuillDrawAndTokenEffect extends OneShotEffect {

    MariTheKillingQuillDrawAndTokenEffect() {
        super(Outcome.Benefit);
    }

    private MariTheKillingQuillDrawAndTokenEffect(final MariTheKillingQuillDrawAndTokenEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        Effect drawCardEffect = new DrawCardSourceControllerEffect(1);
        Effect createTreasureEffect = new CreateTokenEffect(new TreasureToken(), 2);

        boolean success = drawCardEffect.apply(game, source);
        success |= createTreasureEffect.apply(game, source);

        return success;
    }

    @Override
    public MariTheKillingQuillDrawAndTokenEffect copy() {
        return new MariTheKillingQuillDrawAndTokenEffect(this);
    }
}

// Based on Patron of the Vein
class MariTheKillingQuillCreatureDiesAbility extends TriggeredAbilityImpl {

    public MariTheKillingQuillCreatureDiesAbility() {
        super(Zone.BATTLEFIELD, new MariTheKillingQuillExileCreatureEffect(), false);
    }

    public MariTheKillingQuillCreatureDiesAbility(final MariTheKillingQuillCreatureDiesAbility ability) {
        super(ability);
    }

    @Override
    public MariTheKillingQuillCreatureDiesAbility copy() {
        return new MariTheKillingQuillCreatureDiesAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((ZoneChangeEvent) event).isDiesEvent()) {
            if (game.getOpponents(this.controllerId).contains(event.getPlayerId())) {
                Card creature = game.getPermanentOrLKIBattlefield(event.getTargetId());
                if (creature != null
                        && creature.isCreature(game)) {
                    for (Effect effect : this.getEffects()) {
                        effect.setTargetPointer(new FixedTarget(creature.getId(), game));
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature an opponent controls dies, exile it with a hit counter on it.";
    }
}

class MariTheKillingQuillExileCreatureEffect extends OneShotEffect {

    public MariTheKillingQuillExileCreatureEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile it with a hit counter on it";
    }

    public MariTheKillingQuillExileCreatureEffect(final MariTheKillingQuillExileCreatureEffect effect) {
        super(effect);
    }

    @Override
    public MariTheKillingQuillExileCreatureEffect copy() {
        return new MariTheKillingQuillExileCreatureEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card card = game.getCard(this.getTargetPointer().getFirst(game, source));

        if (card != null) {
            return CardUtil.moveCardWithCounter(game, source, controller, card, Zone.EXILED, CounterType.HIT.createInstance());
        }

        return false;

    }
}
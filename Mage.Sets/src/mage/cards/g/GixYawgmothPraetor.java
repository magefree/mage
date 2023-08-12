package mage.cards.g;

import java.util.Set;
import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardXTargetCost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author weirddan455
 */
public final class GixYawgmothPraetor extends CardImpl {

    public GixYawgmothPraetor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.PRAETOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a creature deals combat damage to one of your opponents, its controller may pay 1 life. If they do, they draw a card.
        this.addAbility(new GixYawgmothPraetorTriggeredAbility());

        // {4}{B}{B}{B}, Discard X cards: Exile the top X cards of target opponent's library. You may play land cards and cast spells from among cards exiled this way without paying their mana costs.
        Ability ability = new SimpleActivatedAbility(new GixYawgmothPraetorExileEffect(), new ManaCostsImpl<>("{4}{B}{B}{B}"));
        ability.addCost(new DiscardXTargetCost(StaticFilters.FILTER_CARD_CARDS));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private GixYawgmothPraetor(final GixYawgmothPraetor card) {
        super(card);
    }

    @Override
    public GixYawgmothPraetor copy() {
        return new GixYawgmothPraetor(this);
    }
}

class GixYawgmothPraetorTriggeredAbility extends TriggeredAbilityImpl {

    public GixYawgmothPraetorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GixYawgmothPraetorDrawEffect());
        setTriggerPhrase("Whenever a creature deals combat damage to one of your opponents, ");
    }

    private GixYawgmothPraetorTriggeredAbility(final GixYawgmothPraetorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GixYawgmothPraetorTriggeredAbility copy() {
        return new GixYawgmothPraetorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedEvent damagedEvent = (DamagedEvent) event;
        if (damagedEvent.isCombatDamage() && game.getOpponents(controllerId).contains(damagedEvent.getTargetId())) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(damagedEvent.getSourceId());
            if (permanent != null && permanent.isCreature(game)) {
                getEffects().setTargetPointer(new FixedTarget(permanent.getControllerId()));
                return true;
            }
        }
        return false;
    }
}

class GixYawgmothPraetorDrawEffect extends DoIfCostPaid {

    public GixYawgmothPraetorDrawEffect() {
        super(new DrawCardTargetEffect(1), new PayLifeCost(1), "Pay 1 life and draw a card?");
        this.staticText = "its controller may pay 1 life. If they do, they draw a card";
    }

    private GixYawgmothPraetorDrawEffect(final GixYawgmothPraetorDrawEffect effect) {
        super(effect);
    }

    @Override
    public GixYawgmothPraetorDrawEffect copy() {
        return new GixYawgmothPraetorDrawEffect(this);
    }

    @Override
    protected Player getPayingPlayer(Game game, Ability source) {
        return game.getPlayer(targetPointer.getFirst(game, source));
    }
}

class GixYawgmothPraetorExileEffect extends OneShotEffect {

    public GixYawgmothPraetorExileEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "Exile the top X cards of target opponent's library. You may play land cards and cast spells from among cards exiled this way without paying their mana costs.";
    }

    private GixYawgmothPraetorExileEffect(final GixYawgmothPraetorExileEffect effect) {
        super(effect);
    }

    @Override
    public GixYawgmothPraetorExileEffect copy() {
        return new GixYawgmothPraetorExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (controller == null || opponent == null) {
            return false;
        }
        int xValue = GetXValue.instance.calculate(game, source, this);
        Set<Card> toExile = opponent.getLibrary().getTopCards(game, xValue);
        controller.moveCards(toExile, Zone.EXILED, source, game);
        Cards cards = new CardsImpl(toExile);
        cards.retainZone(Zone.EXILED, game);
        CardUtil.castMultipleWithAttributeForFree(controller, source, game, cards, StaticFilters.FILTER_CARD, Integer.MAX_VALUE, null, true);
        return true;
    }
}

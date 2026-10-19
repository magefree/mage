package mage.cards.c;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.DiscardSourceCost;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

import java.util.Collections;
import java.util.UUID;

public final class CalimDjinnEmperor extends CardImpl {

    public CalimDjinnEmperor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DJINN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}"), false));
        Ability ability = new SimpleActivatedAbility(Zone.HAND, new CalimBreathEffect(), new ManaCostsImpl<>("{1}{U}"));
        ability.addCost(new CalimDiscardCost());
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_PERMANENT_NON_LAND, false));
        ability.withFlavorWord("Calim's Breath");
        this.addAbility(ability);
        this.addAbility(new CalimDiscardTriggeredAbility());
    }

    private CalimDjinnEmperor(final CalimDjinnEmperor card) {
        super(card);
    }

    @Override
    public CalimDjinnEmperor copy() {
        return new CalimDjinnEmperor(this);
    }
}

class CalimBreathEffect extends OneShotEffect {

    CalimBreathEffect() {
        super(Outcome.Benefit);
        staticText = "tap up to one target nonland permanent. Draw a card. Then you may exile two other cards named Calim, Djinn Emperor from your graveyard. When you do, return Calim from your graveyard to the battlefield tapped";
    }

    private CalimBreathEffect(final CalimBreathEffect effect) {
        super(effect);
    }

    @Override
    public CalimBreathEffect copy() {
        return new CalimBreathEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            permanent.tap(source, game);
        }
        player.drawCards(1, source, game);
        FilterCard filter = new FilterCard("other cards named Calim, Djinn Emperor");
        filter.add(new NamePredicate("Calim, Djinn Emperor"));
        filter.add(Predicates.not(new CardIdPredicate(source.getSourceId())));
        CalimDiscardCost discard = source.getCosts().stream().filter(CalimDiscardCost.class::isInstance)
                .map(CalimDiscardCost.class::cast).findFirst().orElse(null);
        if (discard == null || discard.getDiscarded() == null) {
            return true;
        }
        ReflexiveTriggeredAbility reflexive = new ReflexiveTriggeredAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect(true).setTargetPointer(new FixedTarget(discard.getDiscarded())), false);
        return new DoWhenCostPaid(reflexive, new ExileFromGraveCost(new TargetCardInYourGraveyard(2, filter)),
                "Exile two other cards named Calim, Djinn Emperor?")
                .setTargetPointer(new FixedTarget(discard.getDiscarded())).apply(game, source);
    }
}

class CalimDiscardCost extends DiscardSourceCost {

    private MageObjectReference discarded;

    CalimDiscardCost() {
    }

    private CalimDiscardCost(final CalimDiscardCost cost) {
        super(cost);
        this.discarded = cost.discarded;
    }

    @Override
    public CalimDiscardCost copy() {
        return new CalimDiscardCost(this);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        boolean result = super.pay(ability, game, source, controllerId, noMana, costToPay);
        discarded = result ? new MageObjectReference(game.getCard(source.getSourceId()), game) : null;
        return result;
    }

    MageObjectReference getDiscarded() {
        return discarded;
    }
}

class CalimDiscardTriggeredAbility extends TriggeredAbilityImpl {

    CalimDiscardTriggeredAbility() {
        super(Zone.ALL, new CalimConjureEffect(), false);
        setTriggerPhrase("When you discard {this}, ");
    }

    private CalimDiscardTriggeredAbility(final CalimDiscardTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CalimDiscardTriggeredAbility copy() {
        return new CalimDiscardTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DISCARDED_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getTargetId().equals(getSourceId());
    }
}

class CalimConjureEffect extends OneShotEffect {

    CalimConjureEffect() {
        super(Outcome.Benefit);
        staticText = "conjure a card named Calim, Djinn Emperor into your library seventh from the top";
    }

    private CalimConjureEffect(final CalimConjureEffect effect) {
        super(effect);
    }

    @Override
    public CalimConjureEffect copy() {
        return new CalimConjureEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        CardInfo info = CardRepository.instance.findCards(new CardCriteria().name("Calim, Djinn Emperor")).stream().findFirst().orElse(null);
        if (player == null || info == null) {
            return false;
        }
        Card card = info.createCard();
        game.loadCards(Collections.singleton(card), player.getId());
        return player.putCardsOnTopXOfLibrary(new CardsImpl(card), game, source, 7, false);
    }
}

package mage.cards.c;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlaneswalkerEntersWithLoyaltyCountersAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.*;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPlaneswalkerPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.game.permanent.token.YoungPyromancerElementalToken;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

import static mage.constants.Outcome.Benefit;

/**
 * @author TheElk801
 */
public final class ChandraAcolyteOfFlame extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPlaneswalkerPermanent("red planeswalker you control");
    private static final FilterCard filter2
            = new FilterInstantOrSorceryCard("instant or sorcery card with converted mana cost 3 or less");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
        filter2.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, 4));
    }

    public ChandraAcolyteOfFlame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{R}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.CHANDRA);
        this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(4));

        // 0: Put a loyalty counter on each red planeswalker you control.
        this.addAbility(new LoyaltyAbility(new AddCountersAllEffect(CounterType.LOYALTY.createInstance(), filter), 0));

        // 0: Create two 1/1 red Elemental creature tokens. They gain haste. Sacrifice them at the beginning of the next end step.
        this.addAbility(new LoyaltyAbility(new ChandraAcolyteOfFlameEffect(), 0));

        // -2: You may cast target instant or sorcery card with converted mana cost 3 or less from your graveyard. If that card would be put into your graveyard this turn, exile it instead.
        Ability ability = new LoyaltyAbility(new ChandraAcolyteOfFlameGraveyardEffect(), -2);
        ability.addTarget(new TargetCardInYourGraveyard(filter2));
        this.addAbility(ability);
    }

    private ChandraAcolyteOfFlame(final ChandraAcolyteOfFlame card) {
        super(card);
    }

    @Override
    public ChandraAcolyteOfFlame copy() {
        return new ChandraAcolyteOfFlame(this);
    }
}

class ChandraAcolyteOfFlameEffect extends OneShotEffect {

    ChandraAcolyteOfFlameEffect() {
        super(Benefit);
        staticText = "Create two 1/1 red Elemental creature tokens. They gain haste. " +
                "Sacrifice them at the beginning of the next end step.";
    }

    private ChandraAcolyteOfFlameEffect(final ChandraAcolyteOfFlameEffect effect) {
        super(effect);
    }

    @Override
    public ChandraAcolyteOfFlameEffect copy() {
        return new ChandraAcolyteOfFlameEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new YoungPyromancerElementalToken();
        token.putOntoBattlefield(2, game, source.getSourceId(), source.getControllerId());

        token.getLastAddedTokenIds().stream().forEach(permId -> {
            Permanent permanent = game.getPermanent(permId);
            if (permanent == null) {
                return;
            }

            ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.Custom);
            effect.setTargetPointer(new FixedTarget(permId, game));
            game.addEffect(effect, source);

            Effect effect2 = new SacrificeTargetEffect();
            effect2.setTargetPointer(new FixedTarget(permId, game));
            game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect2), source);

            // extra info
            InfoEffect.addInfoToPermanent(game, source, permanent, "<i><b>Warning</b>: It will be sacrificed at the beginning of the next end step</i>");
        });

        return true;
    }
}

class ChandraAcolyteOfFlameGraveyardEffect extends OneShotEffect {

    ChandraAcolyteOfFlameGraveyardEffect() {
        super(Benefit);
        this.staticText = "You may cast target instant or sorcery card " +
                "with converted mana cost 3 or less from your graveyard this turn. " +
                "If that card would be put into your graveyard this turn, exile it instead";
    }

    private ChandraAcolyteOfFlameGraveyardEffect(final ChandraAcolyteOfFlameGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public ChandraAcolyteOfFlameGraveyardEffect copy() {
        return new ChandraAcolyteOfFlameGraveyardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(this.getTargetPointer().getFirst(game, source));
        if (card != null) {
            ContinuousEffect effect = new ChandraAcolyteOfFlameCastFromGraveyardEffect();
            effect.setTargetPointer(new FixedTarget(card.getId(), card.getZoneChangeCounter(game)));
            game.addEffect(effect, source);
            effect = new ChandraAcolyteOfFlameReplacementEffect(card.getId());
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }
}

class ChandraAcolyteOfFlameCastFromGraveyardEffect extends AsThoughEffectImpl {

    ChandraAcolyteOfFlameCastFromGraveyardEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Benefit);
    }

    private ChandraAcolyteOfFlameCastFromGraveyardEffect(final ChandraAcolyteOfFlameCastFromGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public ChandraAcolyteOfFlameCastFromGraveyardEffect copy() {
        return new ChandraAcolyteOfFlameCastFromGraveyardEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return objectId.equals(this.getTargetPointer().getFirst(game, source)) && affectedControllerId.equals(source.getControllerId());
    }
}

class ChandraAcolyteOfFlameReplacementEffect extends ReplacementEffectImpl {

    private final UUID cardId;

    ChandraAcolyteOfFlameReplacementEffect(UUID cardId) {
        super(Duration.EndOfTurn, Outcome.Exile);
        this.cardId = cardId;
        staticText = "If that card would be put into your graveyard this turn, exile it instead";
    }

    private ChandraAcolyteOfFlameReplacementEffect(final ChandraAcolyteOfFlameReplacementEffect effect) {
        super(effect);
        this.cardId = effect.cardId;
    }

    @Override
    public ChandraAcolyteOfFlameReplacementEffect copy() {
        return new ChandraAcolyteOfFlameReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(this.cardId);
        if (controller != null && card != null) {
            controller.moveCardToExileWithInfo(card, null, "", source.getSourceId(), game, Zone.STACK, true);
            return true;
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        return zEvent.getToZone() == Zone.GRAVEYARD
                && zEvent.getTargetId().equals(this.cardId);
    }
}

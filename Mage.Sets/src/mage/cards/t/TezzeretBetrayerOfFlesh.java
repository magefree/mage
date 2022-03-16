package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.command.emblems.TezzeretBetrayerOfFleshEmblem;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetArtifactPermanent;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TezzeretBetrayerOfFlesh extends CardImpl {

    public TezzeretBetrayerOfFlesh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{U}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.TEZZERET);
        this.setStartingLoyalty(4);

        // The first activated ability of an artifact you activate each turn costs {2} less to activate.
        this.addAbility(new SimpleStaticAbility(
                new TezzeretBetrayerOfFleshReductionEffect()
        ), new TezzeretBetrayerOfFleshWatcher());

        // +1: Draw two cards. Then discard two cards unless you discard an artifact card.
        Ability ability = new LoyaltyAbility(new DrawCardSourceControllerEffect(2), 1);
        ability.addEffect(new DoIfCostPaid(
                null, new DiscardControllerEffect(2),
                new DiscardCardCost(StaticFilters.FILTER_CARD_ARTIFACT_AN)
                        .setText("discard an artifact card instead of discarding two cards")
        ).setText("Then discard two cards unless you discard an artifact card"));
        this.addAbility(ability);

        // −2: Target artifact becomes an artifact creature. If it isn't a Vehicle, it has base power and toughness 4/4.
        ability = new LoyaltyAbility(new TezzeretBetrayerOfFleshTypeEffect(), -2);
        ability.addTarget(new TargetArtifactPermanent());
        this.addAbility(ability);

        // −6: You get an emblem with "Whenever an artifact you control becomes tapped, draw a card."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new TezzeretBetrayerOfFleshEmblem()), -6));
    }

    private TezzeretBetrayerOfFlesh(final TezzeretBetrayerOfFlesh card) {
        super(card);
    }

    @Override
    public TezzeretBetrayerOfFlesh copy() {
        return new TezzeretBetrayerOfFlesh(this);
    }
}

class TezzeretBetrayerOfFleshReductionEffect extends CostModificationEffectImpl {

    TezzeretBetrayerOfFleshReductionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "the first activated ability of an artifact you activate each turn costs {2} less to activate";
    }

    private TezzeretBetrayerOfFleshReductionEffect(final TezzeretBetrayerOfFleshReductionEffect effect) {
        super(effect);
    }

    @Override
    public TezzeretBetrayerOfFleshReductionEffect copy() {
        return new TezzeretBetrayerOfFleshReductionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.reduceCost(abilityToModify, 2);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify.isControlledBy(source.getControllerId())
                && abilityToModify instanceof ActivatedAbility
                && TezzeretBetrayerOfFleshWatcher.checkPlayer(game, abilityToModify);
    }
}

class TezzeretBetrayerOfFleshWatcher extends Watcher {

    private final Set<UUID> playerSet = new HashSet<>();

    TezzeretBetrayerOfFleshWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ACTIVATED_ABILITY) {
            return;
        }
        Permanent permanent = game.getPermanent(event.getSourceId());
        if (permanent != null && permanent.isArtifact(game)) {
            playerSet.add(event.getPlayerId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        playerSet.clear();
    }

    public static boolean checkPlayer(Game game, Ability ability) {
        if (game.getState()
                .getWatcher(TezzeretBetrayerOfFleshWatcher.class)
                .playerSet
                .contains(ability.getControllerId())) {
            return false;
        }
        Permanent permanent = ability.getSourcePermanentOrLKI(game);
        return permanent != null && permanent.isArtifact(game);
    }
}

class TezzeretBetrayerOfFleshTypeEffect extends ContinuousEffectImpl {

    TezzeretBetrayerOfFleshTypeEffect() {
        super(Duration.Custom, Outcome.BecomeCreature);
        staticText = "target artifact becomes an artifact creature. " +
                "If it isn't a Vehicle, it has base power and toughness 4/4";
    }

    private TezzeretBetrayerOfFleshTypeEffect(final TezzeretBetrayerOfFleshTypeEffect effect) {
        super(effect);
    }

    @Override
    public TezzeretBetrayerOfFleshTypeEffect copy() {
        return new TezzeretBetrayerOfFleshTypeEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            discard();
            return false;
        }
        switch (layer) {
            case TypeChangingEffects_4:
                permanent.addCardType(game, CardType.ARTIFACT, CardType.CREATURE);
                return true;
            case PTChangingEffects_7:
                if (sublayer != SubLayer.SetPT_7b
                        || permanent.hasSubtype(SubType.VEHICLE, game)) {
                    return false;
                }
                permanent.getPower().setValue(4);
                permanent.getToughness().setValue(4);
                return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        switch (layer) {
            case TypeChangingEffects_4:
            case PTChangingEffects_7:
                return true;
        }
        return false;
    }
}

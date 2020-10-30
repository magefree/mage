package mage.cards.y;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YasharnImplacableEarth extends CardImpl {

    public YasharnImplacableEarth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.BOAR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Yasharn, Implacable Earth enters the battlefield, search your library for a basic Forest card and a basic Plains card, reveal those cards, put them into your hand, then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutInHandEffect(new YasharnImplacableEarthTarget(), true)
                        .setText("search your library for a basic Forest card and a basic Plains card, " +
                                "reveal those cards, put them into your hand, then shuffle your library")
        ));

        // Players can't pay life or sacrifice nonland permanents to cast spells or activate abilities.
        Ability ability = new SimpleStaticAbility(new YasharnImplacableEarthEffect());
        ability.addEffect(new YasharnImplacableEarthSacrificeFilterEffect());
        this.addAbility(ability);
    }

    private YasharnImplacableEarth(final YasharnImplacableEarth card) {
        super(card);
    }

    @Override
    public YasharnImplacableEarth copy() {
        return new YasharnImplacableEarth(this);
    }
}

class YasharnImplacableEarthTarget extends TargetCardInLibrary {

    private static final FilterCard filter
            = new FilterCard("a basic Forest card and a basic Plains card");

    static {
        filter.add(SuperType.BASIC.getPredicate());
        filter.add(Predicates.or(
                SubType.FOREST.getPredicate(),
                SubType.PLAINS.getPredicate()
        ));
    }

    YasharnImplacableEarthTarget() {
        super(0, 2, filter);
    }

    private YasharnImplacableEarthTarget(final YasharnImplacableEarthTarget target) {
        super(target);
    }

    @Override
    public YasharnImplacableEarthTarget copy() {
        return new YasharnImplacableEarthTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(playerId, id, source, game)) {
            return false;
        }
        Card card = game.getCard(id);
        return card != null
                && this
                .getTargets()
                .stream()
                .map(game::getCard)
                .filter(Objects::nonNull)
                .map(c -> c.getSubtype(game))
                .flatMap(Collection::stream)
                .filter(subType -> subType == SubType.FOREST || subType == SubType.PLAINS)
                .noneMatch(subType -> card.hasSubtype(subType, game));
    }
}

class YasharnImplacableEarthEffect extends ContinuousEffectImpl {

    YasharnImplacableEarthEffect() {
        super(Duration.WhileOnBattlefield, Layer.PlayerEffects, SubLayer.NA, Outcome.Detriment);
        staticText = "Players can't pay life or sacrifice nonland permanents to cast spells or activate abilities";
    }

    private YasharnImplacableEarthEffect(final YasharnImplacableEarthEffect effect) {
        super(effect);
    }

    @Override
    public YasharnImplacableEarthEffect copy() {
        return new YasharnImplacableEarthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            player.setCanPayLifeCost(false);
            player.setCanPaySacrificeCostFilter(StaticFilters.FILTER_PERMANENTS_NON_LAND);
        }
        return true;
    }
}

class YasharnImplacableEarthSacrificeFilterEffect extends CostModificationEffectImpl {

    YasharnImplacableEarthSacrificeFilterEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, CostModificationType.SET_COST);
    }

    private YasharnImplacableEarthSacrificeFilterEffect(YasharnImplacableEarthSacrificeFilterEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        for (Cost cost : abilityToModify.getCosts()) {
            if (cost instanceof SacrificeTargetCost) {
                ((SacrificeTargetCost) cost)
                        .getTargets()
                        .get(0)
                        .getFilter()
                        .add(CardType.LAND.getPredicate());
            }
        }
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return (abilityToModify.getAbilityType() == AbilityType.ACTIVATED
                || abilityToModify instanceof SpellAbility)
                && game.getState().getPlayersInRange(source.getControllerId(), game).contains(abilityToModify.getControllerId());
    }

    @Override
    public YasharnImplacableEarthSacrificeFilterEffect copy() {
        return new YasharnImplacableEarthSacrificeFilterEffect(this);
    }
}

package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledToBattlefieldAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInExile;
import mage.target.targetadjustment.TargetAdjuster;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GelatinousCube extends CardImpl {

    private static final FilterPermanent filter = new FilterOpponentsCreaturePermanent("non-Ooze creature");

    static {
        filter.add(Predicates.not(SubType.OOZE.getPredicate()));
    }

    public GelatinousCube(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        this.subtype.add(SubType.OOZE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Engulf — When Gelatinous Cube enters the battlefield, exile target non-Ooze creature an opponent controls until Gelatinous Cube leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect(filter.getMessage()));
        ability.addTarget(new TargetPermanent(filter));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledToBattlefieldAbility()));
        this.addAbility(ability.withFlavorWord("Engulf"));

        // Dissolve — {X}{B}: Put target creature card with mana value X exiled with Gelatinous Cube into its owner's graveyard.
        ability = new SimpleActivatedAbility(new GelatinousCubeEffect(), new ManaCostsImpl<>("{X}{B}"));
        ability.setTargetAdjuster(GelatinousCubeAdjuster.instance);
        this.addAbility(ability.withFlavorWord("Dissolve"));
    }

    private GelatinousCube(final GelatinousCube card) {
        super(card);
    }

    @Override
    public GelatinousCube copy() {
        return new GelatinousCube(this);
    }
}

enum GelatinousCubeAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        int xValue = ability.getManaCostsToPay().getX();
        FilterCard filter = new FilterCreatureCard("creature card with mana value " + xValue);
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, xValue));
        ability.addTarget(new TargetCardInExile(filter, CardUtil.getExileZoneId(game, ability)));
    }
}

class GelatinousCubeEffect extends OneShotEffect {

    GelatinousCubeEffect() {
        super(Outcome.Benefit);
        staticText = "put target creature card with mana value X exiled with {this} into its owner's graveyard";
    }

    private GelatinousCubeEffect(final GelatinousCubeEffect effect) {
        super(effect);
    }

    @Override
    public GelatinousCubeEffect copy() {
        return new GelatinousCubeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        return player != null && card != null && player.moveCards(card, Zone.GRAVEYARD, source, game);
    }
}

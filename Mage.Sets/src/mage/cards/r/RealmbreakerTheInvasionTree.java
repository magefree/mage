package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RealmbreakerTheInvasionTree extends CardImpl {

    private static final FilterCard filter = new FilterCard("Praetor cards");

    static {
        filter.add(SubType.PRAETOR.getPredicate());
    }

    public RealmbreakerTheInvasionTree(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.supertype.add(SuperType.LEGENDARY);

        // {2}, {T}: Target opponent mills three cards. Put a land card from their graveyard onto the battlefield tapped under your control. It gains "If this land would leave the battlefield, exile it instead of putting it anywhere else."
        Ability ability = new SimpleActivatedAbility(new MillCardsTargetEffect(3), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new RealmbreakerTheInvasionTreeEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // {10}, {T}, Sacrifice Realmbreaker, the Invasion Tree: Search your library for any number of Praetor cards, put them onto the battlefield, then shuffle.
        ability = new SimpleActivatedAbility(new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(0, Integer.MAX_VALUE, filter)
        ), new GenericManaCost(10));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private RealmbreakerTheInvasionTree(final RealmbreakerTheInvasionTree card) {
        super(card);
    }

    @Override
    public RealmbreakerTheInvasionTree copy() {
        return new RealmbreakerTheInvasionTree(this);
    }
}

class RealmbreakerTheInvasionTreeEffect extends OneShotEffect {

    RealmbreakerTheInvasionTreeEffect() {
        super(Outcome.Benefit);
        staticText = "Put a land card from their graveyard onto the battlefield tapped under your control. " +
                "It gains \"If this land would leave the battlefield, exile it instead of putting it anywhere else.\"";
    }

    private RealmbreakerTheInvasionTreeEffect(final RealmbreakerTheInvasionTreeEffect effect) {
        super(effect);
    }

    @Override
    public RealmbreakerTheInvasionTreeEffect copy() {
        return new RealmbreakerTheInvasionTreeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || opponent == null
                || opponent.getGraveyard().count(StaticFilters.FILTER_CARD_LAND, game) < 1) {
            return false;
        }
        TargetCard target = new TargetCardInGraveyard(StaticFilters.FILTER_CARD_LAND);
        target.setNotTarget(true);
        controller.choose(Outcome.PutLandInPlay, opponent.getGraveyard(), target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        controller.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, false, null);
        Permanent permanent = game.getPermanent(card.getId());
        if (permanent == null) {
            return false;
        }
        game.addEffect(new GainAbilityTargetEffect(
                new SimpleStaticAbility(new RealmbreakerTheInvasionTreeReplacementEffect()), Duration.Custom
        ).setTargetPointer(new FixedTarget(permanent, game)), source);
        return true;
    }
}

class RealmbreakerTheInvasionTreeReplacementEffect extends ReplacementEffectImpl {

    RealmbreakerTheInvasionTreeReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Tap);
        staticText = "If this land would leave the battlefield, exile it instead of putting it anywhere else";
    }

    private RealmbreakerTheInvasionTreeReplacementEffect(final RealmbreakerTheInvasionTreeReplacementEffect effect) {
        super(effect);
    }

    @Override
    public RealmbreakerTheInvasionTreeReplacementEffect copy() {
        return new RealmbreakerTheInvasionTreeReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ((ZoneChangeEvent) event).setToZone(Zone.EXILED);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId().equals(source.getSourceId())
                && ((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD
                && ((ZoneChangeEvent) event).getToZone() != Zone.EXILED;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }
}

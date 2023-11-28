package mage.cards.o;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.CardTypeAssignment;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class OjerKaslemDeepestGrowth extends CardImpl {

    public OjerKaslemDeepestGrowth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        this.secondSideCardClazz = mage.cards.t.TempleOfCultivation.class;

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Ojer Kaslem deals combat damage to a player, reveal that many cards from the top of your library. You may put a creature card and/or a land card from among them onto the battlefield. Put the rest on the bottom in a random order.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new OjerKaslemDeepestGrowthEffect(), false, true));

        // When Ojer Kaslem dies, return it to the battlefield tapped and transformed under its owner's control.
        this.addAbility(new TransformAbility());
        this.addAbility(new DiesSourceTriggeredAbility(new OjerKaslemDeepestGrowthTransformEffect()));
    }

    private OjerKaslemDeepestGrowth(final OjerKaslemDeepestGrowth card) {
        super(card);
    }

    @Override
    public OjerKaslemDeepestGrowth copy() {
        return new OjerKaslemDeepestGrowth(this);
    }
}

class OjerKaslemDeepestGrowthTransformEffect extends OneShotEffect {

    OjerKaslemDeepestGrowthTransformEffect() {
        super(Outcome.Benefit);
        staticText = "return it to the battlefield tapped and transformed under its owner's control";
    }

    private OjerKaslemDeepestGrowthTransformEffect(final OjerKaslemDeepestGrowthTransformEffect effect) {
        super(effect);
    }

    @Override
    public OjerKaslemDeepestGrowthTransformEffect copy() {
        return new OjerKaslemDeepestGrowthTransformEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        MageObject sourceObject = source.getSourceObjectIfItStillExists(game);
        if (!(sourceObject instanceof Card)) {
            return false;
        }
        game.getState().setValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + source.getSourceId(), Boolean.TRUE);
        controller.moveCards((Card) sourceObject, Zone.BATTLEFIELD, source, game, true, false, true, null);
        return true;
    }
}

/**
 * Inspired by {@link mage.cards.g.GishathSunsAvatar}
 */
class OjerKaslemDeepestGrowthEffect extends OneShotEffect {

    OjerKaslemDeepestGrowthEffect() {
        super(Outcome.Benefit);
        this.staticText = "reveal that many cards from the top of your library. "
                + "You may put a creature card and/or a land card from among them onto the battlefield. "
                + "Put the rest on the bottom in a random order";
    }

    private OjerKaslemDeepestGrowthEffect(final OjerKaslemDeepestGrowthEffect effect) {
        super(effect);
    }

    @Override
    public OjerKaslemDeepestGrowthEffect copy() {
        return new OjerKaslemDeepestGrowthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int xValue = (Integer) getValue("damage");
        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, xValue));
        if (!cards.isEmpty()) {
            controller.revealCards(source, cards, game);
            TargetCard target = new OjerKaslemDeepestGrowthTarget();
            controller.choose(Outcome.PutCardInPlay, cards, target, source, game);
            Cards toBattlefield = new CardsImpl(target.getTargets());
            cards.removeAll(toBattlefield);
            controller.moveCards(toBattlefield.getCards(game), Zone.BATTLEFIELD, source, game, false, false, false, null);
            controller.putCardsOnBottomOfLibrary(cards, game, source, false);
        }
        return true;
    }
}

class OjerKaslemDeepestGrowthTarget extends TargetCardInLibrary {

    private static final FilterCard filter
            = new FilterCard("a creature card and/or a land card");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.LAND.getPredicate()
        ));
    }

    private static final CardTypeAssignment cardTypeAssigner
            = new CardTypeAssignment(CardType.CREATURE, CardType.LAND);

    OjerKaslemDeepestGrowthTarget() {
        super(0, 2, filter);
    }

    private OjerKaslemDeepestGrowthTarget(final OjerKaslemDeepestGrowthTarget target) {
        super(target);
    }

    @Override
    public OjerKaslemDeepestGrowthTarget copy() {
        return new OjerKaslemDeepestGrowthTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(playerId, id, source, game)) {
            return false;
        }
        Card card = game.getCard(id);
        if (card == null) {
            return false;
        }
        if (this.getTargets().isEmpty()) {
            return true;
        }
        Cards cards = new CardsImpl(this.getTargets());
        cards.add(card);
        return cardTypeAssigner.getRoleCount(cards, game) >= cards.size();
    }
}

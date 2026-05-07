package mage.cards.b;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ConniveSourceEffect;
import mage.abilities.keyword.BoastAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author muz
 */
public final class BaronHelmutZemo extends CardImpl {

    private static final FilterSpell blackSpellFilter = new FilterSpell("a black spell");

    static {
        blackSpellFilter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public BaronHelmutZemo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you cast a black spell from your hand, Baron Helmut Zemo connives.
        this.addAbility(SpellCastControllerTriggeredAbility.createWithFromZone(
            new ConniveSourceEffect("{this}"), blackSpellFilter, false, Zone.HAND
        ));

        // Boast — Exile any number of black cards from your graveyard with fifteen or more black mana symbols
        // among their mana costs: Copy those exiled cards. You may cast up to three of the copies without
        // paying their mana costs.
        this.addAbility(new BoastAbility(new BaronHelmutZemoBoastEffect(), new BaronHelmutZemoBoastCost()));
    }

    private BaronHelmutZemo(final BaronHelmutZemo card) {
        super(card);
    }

    @Override
    public BaronHelmutZemo copy() {
        return new BaronHelmutZemo(this);
    }
}

class BaronHelmutZemoBoastCost extends CostImpl {

    private static final FilterCard blackCardFilter = new FilterCard("black cards");

    static {
        blackCardFilter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    BaronHelmutZemoBoastCost() {
        this.text = "exile any number of black cards from your graveyard with fifteen or more"
                + " black mana symbols among their mana costs";
    }

    private BaronHelmutZemoBoastCost(final BaronHelmutZemoBoastCost cost) {
        super(cost);
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Player player = game.getPlayer(controllerId);
        if (player == null) {
            return false;
        }
        int total = player.getGraveyard().getCards(blackCardFilter, controllerId, source, game)
                .stream()
                .mapToInt(BaronHelmutZemoBoastCost::countBlackManaSymbols)
                .sum();
        return total >= 15;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player player = game.getPlayer(controllerId);
        if (player == null) {
            return false;
        }
        TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(0, Integer.MAX_VALUE, blackCardFilter);
        target.withNotTarget(true);
        target.choose(Outcome.Exile, controllerId, source.getSourceId(), source, game);
        Cards chosen = new CardsImpl();
        for (UUID targetId : target.getTargets()) {
            Card card = game.getCard(targetId);
            if (card != null) {
                chosen.add(card);
            }
        }
        int total = chosen.getCards(game)
                .stream()
                .mapToInt(BaronHelmutZemoBoastCost::countBlackManaSymbols)
                .sum();
        if (total < 15) {
            return false;
        }
        paid = player.moveCardsToExile(
                chosen.getCards(game),
                source,
                game,
                true,
                CardUtil.getExileZoneId(game, source),
                CardUtil.getSourceName(game, source)
        );
        return paid;
    }

    @Override
    public BaronHelmutZemoBoastCost copy() {
        return new BaronHelmutZemoBoastCost(this);
    }

    static int countBlackManaSymbols(Card card) {
        return (int) card.getManaCost()
                .stream()
                .filter(mc -> mc.containsColor(ColoredManaSymbol.B))
                .count();
    }
}

class BaronHelmutZemoBoastEffect extends OneShotEffect {

    BaronHelmutZemoBoastEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "copy those exiled cards. You may cast up to three of the copies"
                + " without paying their mana costs";
    }

    private BaronHelmutZemoBoastEffect(final BaronHelmutZemoBoastEffect effect) {
        super(effect);
    }

    @Override
    public BaronHelmutZemoBoastEffect copy() {
        return new BaronHelmutZemoBoastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        if (player == null || exileZone == null || exileZone.isEmpty()) {
            return false;
        }
        Cards copies = new CardsImpl();
        for (UUID cardId : exileZone) {
            Card card = game.getCard(cardId);
            if (card != null) {
                copies.add(game.copyCard(card, source, source.getControllerId()));
            }
        }
        if (copies.isEmpty()) {
            return false;
        }
        CardUtil.castMultipleWithAttributeForFree(player, source, game, copies, StaticFilters.FILTER_CARD, 3);
        return true;
    }
}

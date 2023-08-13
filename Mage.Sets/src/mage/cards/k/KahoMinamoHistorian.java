package mage.cards.k;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.SearchEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class KahoMinamoHistorian extends CardImpl {

    public KahoMinamoHistorian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Kaho, Minamo Historian enters the battlefield, search your library for up to three 
        // instant cards and exile them. Then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new KahoMinamoHistorianEffect(), false));

        // {X}, {tap}: You may cast a card with converted mana cost X exiled with 
        // Kaho without paying its mana cost.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new KahoMinamoHistorianCastEffect(), new ManaCostsImpl<>("{X}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private KahoMinamoHistorian(final KahoMinamoHistorian card) {
        super(card);
    }

    @Override
    public KahoMinamoHistorian copy() {
        return new KahoMinamoHistorian(this);
    }
}

class KahoMinamoHistorianEffect extends SearchEffect {

    private static final FilterCard filter = new FilterCard("up to three instant cards");

    static {
        filter.add(CardType.INSTANT.getPredicate());
    }

    public KahoMinamoHistorianEffect() {
        super(new TargetCardInLibrary(0, 3, filter), Outcome.Benefit);
        this.staticText = "search your library for up to three instant cards, exile them, then shuffle";
    }

    public KahoMinamoHistorianEffect(final KahoMinamoHistorianEffect effect) {
        super(effect);
    }

    @Override
    public KahoMinamoHistorianEffect copy() {
        return new KahoMinamoHistorianEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller != null && sourceObject != null) {
            if (controller.searchLibrary(target, source, game)) {
                UUID exileZone = CardUtil.getCardExileZoneId(game, source);
                if (!target.getTargets().isEmpty()) {
                    controller.moveCardsToExile(new CardsImpl(target.getTargets()).getCards(game),
                            source, game, true, exileZone, sourceObject.getIdName());
                }
            }
            controller.shuffleLibrary(source, game);
            return true;

        }
        return false;
    }
}

class KahoMinamoHistorianCastEffect extends OneShotEffect {

    public KahoMinamoHistorianCastEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "you may cast a spell with mana value X " +
                "from among cards exiled with {this} without paying its mana cost";
    }

    public KahoMinamoHistorianCastEffect(final KahoMinamoHistorianCastEffect effect) {
        super(effect);
    }

    @Override
    public KahoMinamoHistorianCastEffect copy() {
        return new KahoMinamoHistorianCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getCardExileZoneId(game, source));
        if (controller == null || exileZone == null) {
            return false;
        }

        Cards cards = new CardsImpl(exileZone);
        if (cards.isEmpty()) {
            return false;
        }

        FilterCard filter = new FilterCard();
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, source.getManaCostsToPay().getX()));
        return CardUtil.castSpellWithAttributesForFree(controller, source, game, cards, filter);
    }
}

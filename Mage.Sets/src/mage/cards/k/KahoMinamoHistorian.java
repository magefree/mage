package mage.cards.k;

import java.util.UUID;
import mage.ApprovingObject;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.SearchEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public final class KahoMinamoHistorian extends CardImpl {

    public KahoMinamoHistorian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        this.addSuperType(SuperType.LEGENDARY);
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
                new KahoMinamoHistorianCastEffect(), new ManaCostsImpl("{X}"));
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
        this.staticText = "search your library for up to three instant cards "
                + "and exile them. Then shuffle";
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
        MageObject sourceObject = game.getObject(source.getSourceId());
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
        this.staticText = "you may cast a card with mana value X "
                + "exiled with {this} without paying its mana cost";
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
        if (controller != null) {
            FilterCard filter = new FilterCard();
            filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, source.getManaCostsToPay().getX()));
            TargetCardInExile target = new TargetCardInExile(filter, CardUtil.getCardExileZoneId(game, source));
            Cards cards = game.getExile().getExileZone(CardUtil.getCardExileZoneId(game, source));
            if (cards != null
                    && !cards.isEmpty()
                    && controller.choose(Outcome.PlayForFree, cards, target, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
                    controller.cast(controller.chooseAbilityForCast(card, game, true),
                            game, true, new ApprovingObject(source, game));
                    game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
                }
            }
            return true;
        }
        return false;
    }
}

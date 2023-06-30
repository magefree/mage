package mage.cards.m;

import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutOnLibraryEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class MomirVigSimicVisionary extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a green creature spell");
    private static final FilterSpell filter2 = new FilterSpell("a blue creature spell");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
        filter.add(CardType.CREATURE.getPredicate());
        filter2.add(new ColorPredicate(ObjectColor.BLUE));
        filter2.add(CardType.CREATURE.getPredicate());
    }

    public MomirVigSimicVisionary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast a green creature spell, you may search your library for a creature card and reveal it. If you do, shuffle your library and put that card on top of it.
        Effect effect = new SearchLibraryPutOnLibraryEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_CREATURE), true);
        effect.setText("search your library for a creature card, reveal it, then shuffle and put that card on top");
        this.addAbility(new SpellCastControllerTriggeredAbility(effect, filter, true));

        // Whenever you cast a blue creature spell, reveal the top card of your library. If it's a creature card, put that card into your hand.
        this.addAbility(new SpellCastControllerTriggeredAbility(new MomirVigSimicVisionaryEffect(), filter2, false));

    }

    private MomirVigSimicVisionary(final MomirVigSimicVisionary card) {
        super(card);
    }

    @Override
    public MomirVigSimicVisionary copy() {
        return new MomirVigSimicVisionary(this);
    }
}

class MomirVigSimicVisionaryEffect extends OneShotEffect {

    public MomirVigSimicVisionaryEffect() {
        super(Outcome.DrawCard);
        this.staticText = "reveal the top card of your library. If it's a creature card, put that card into your hand";
    }

    public MomirVigSimicVisionaryEffect(final MomirVigSimicVisionaryEffect effect) {
        super(effect);
    }

    @Override
    public MomirVigSimicVisionaryEffect copy() {
        return new MomirVigSimicVisionaryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller == null || sourceObject == null) {
            return false;
        }

        CardsImpl cards = new CardsImpl();
        cards.addAllCards(controller.getLibrary().getTopCards(game, 1));
        controller.revealCards(sourceObject.getIdName(), cards, game);

        Set<Card> cardsList = cards.getCards(game);
        Cards cardsToHand = new CardsImpl();
        for (Card card : cardsList) {
            if (card.isCreature(game)) {
                cardsToHand.add(card);
                cards.remove(card);
            }
        }
        controller.moveCards(cardsToHand, Zone.HAND, source, game);
        return true;
    }
}

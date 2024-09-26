package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.SharesNamePredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class MishraArtificerProdigy extends CardImpl {

    public MishraArtificerProdigy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever you cast an artifact spell, you may search your graveyard, hand, and/or library for a card with the same name as that spell and put it onto the battlefield. If you search your library this way, shuffle it.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new MishraArtificerProdigyEffect(), StaticFilters.FILTER_SPELL_AN_ARTIFACT, true
        ));
    }

    private MishraArtificerProdigy(final MishraArtificerProdigy card) {
        super(card);
    }

    @Override
    public MishraArtificerProdigy copy() {
        return new MishraArtificerProdigy(this);
    }
}

class MishraArtificerProdigyEffect extends OneShotEffect {

    MishraArtificerProdigyEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "search your graveyard, hand, and/or library for a card with the same name as " +
                "that spell and put it onto the battlefield. If you search your library this way, shuffle";
    }

    private MishraArtificerProdigyEffect(final MishraArtificerProdigyEffect effect) {
        super(effect);
    }

    @Override
    public MishraArtificerProdigyEffect copy() {
        return new MishraArtificerProdigyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Spell spell = (Spell) getValue("spellCast");
        if (controller == null || spell == null) {
            return false;
        }
        FilterCard filter = new FilterCard("card with the same name");
        filter.add(new SharesNamePredicate(spell));
        Card card = null;
        // Graveyard
        if (controller.chooseUse(Outcome.Neutral, "Search your graveyard?", source, game)) {
            // You can't fail to find the card in your graveyard because it's not hidden
            TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(1, 1, filter);
            if (controller.choose(Outcome.PutCardInPlay, controller.getGraveyard(), target, source, game)) {
                card = game.getCard(target.getFirstTarget());
            }
        }
        // Hand
        if (card == null && controller.chooseUse(Outcome.Neutral, "Search your hand?", source, game)) {
            TargetCardInHand target = new TargetCardInHand(0, 1, filter);
            if (controller.choose(Outcome.PutCardInPlay, controller.getHand(), target, source, game)) {
                card = game.getCard(target.getFirstTarget());
            }
        }
        // Library
        if (card == null && controller.chooseUse(Outcome.Neutral, "Search your library?", source, game)) {
            TargetCardInLibrary target = new TargetCardInLibrary(0, 1, filter);
            if (controller.searchLibrary(target, source, game)) {
                card = game.getCard(target.getFirstTarget());
            }
            controller.shuffleLibrary(source, game);
        }
        // Put on battlefield
        if (card != null) {
            controller.moveCards(card, Zone.BATTLEFIELD, source, game);
        }
        return true;
    }
}

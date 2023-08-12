
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author fireshoes
 */
public final class LinSivviDefiantHero extends CardImpl {

    private static final FilterCard filter = new FilterCard("Rebel card from your graveyard");

    static {
        filter.add(TargetController.YOU.getOwnerPredicate());
        filter.add(SubType.REBEL.getPredicate());
    }

    static final String rule = "Put target Rebel card from your graveyard on the bottom of your library";

    public LinSivviDefiantHero(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.REBEL);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {X}, {tap}: Search your library for a Rebel permanent card with converted mana cost X or less and put it onto the battlefield. Then shuffle your library.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new LinSivviDefiantHeroEffect(),
                new ManaCostsImpl<>("{X}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {3}: Put target Rebel card from your graveyard on the bottom of your library.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PutOnLibraryTargetEffect(false, rule), new GenericManaCost(3));
        ability.addTarget(new TargetCardInYourGraveyard(1, filter));
        this.addAbility(ability);
    }

    private LinSivviDefiantHero(final LinSivviDefiantHero card) {
        super(card);
    }

    @Override
    public LinSivviDefiantHero copy() {
        return new LinSivviDefiantHero(this);
    }
}

class LinSivviDefiantHeroEffect extends OneShotEffect {

    public LinSivviDefiantHeroEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Search your library for a Rebel permanent card with mana value X or less, put it onto the battlefield, then shuffle";
    }

    public LinSivviDefiantHeroEffect(final LinSivviDefiantHeroEffect effect) {
        super(effect);
    }

    @Override
    public LinSivviDefiantHeroEffect copy() {
        return new LinSivviDefiantHeroEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        int xCost = source.getManaCostsToPay().getX();

        FilterPermanentCard filter = new FilterPermanentCard("Rebel permanent card with mana value " + xCost + " or less");
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, xCost + 1));
        filter.add(SubType.REBEL.getPredicate());
        TargetCardInLibrary target = new TargetCardInLibrary(filter);

        if (controller.searchLibrary(target, source, game)) {
            Card card = controller.getLibrary().getCard(target.getFirstTarget(), game);
            if (card != null) {
                controller.moveCards(card, Zone.BATTLEFIELD, source, game);
            }
            controller.shuffleLibrary(source, game);
            return true;
        }
        controller.shuffleLibrary(source, game);
        return false;
    }
}

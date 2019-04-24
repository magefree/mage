
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author fireshoes
 */
public final class WoodlandBellower extends CardImpl {

    public WoodlandBellower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // When Woodland Bellower enters the battlefield, you may search your library for a nonlegendary green creature card with converted mana cost 3 or less, put it onto the battlefield, then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new WoodlandBellowerEffect(), true));
    }

    public WoodlandBellower(final WoodlandBellower card) {
        super(card);
    }

    @Override
    public WoodlandBellower copy() {
        return new WoodlandBellower(this);
    }
}

class WoodlandBellowerEffect extends OneShotEffect {

    WoodlandBellowerEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Search your library for a nonlegendary green creature card with converted mana cost 3 or less, put it onto the battlefield, then shuffle your library";
    }

    WoodlandBellowerEffect(final WoodlandBellowerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        FilterCard filter = new FilterCard("nonlegendary green creature card with converted mana cost 3 or less");
        filter.add(new ColorPredicate(ObjectColor.GREEN));
        filter.add(new CardTypePredicate(CardType.CREATURE));
        filter.add(Predicates.not(new SupertypePredicate(SuperType.LEGENDARY)));
        filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, 4));
        TargetCardInLibrary target = new TargetCardInLibrary(filter);
        if (controller.searchLibrary(target, game)) {
            if (!target.getTargets().isEmpty()) {
                Card card = controller.getLibrary().getCard(target.getFirstTarget(), game);
                controller.moveCards(card, Zone.BATTLEFIELD, source, game);
            }
            controller.shuffleLibrary(source, game);
            return true;
        }
        controller.shuffleLibrary(source, game);
        return false;
    }

    @Override
    public WoodlandBellowerEffect copy() {
        return new WoodlandBellowerEffect(this);
    }

}

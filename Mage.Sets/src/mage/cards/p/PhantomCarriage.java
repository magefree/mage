package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.SearchEffect;
import mage.abilities.keyword.DisturbAbility;
import mage.abilities.keyword.FlashbackAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PhantomCarriage extends CardImpl {

    public PhantomCarriage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Phantom Carriage enters the battlefield, you may search your library for a card with flashback or disturb, put it into your graveyard, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new PhantomCarriageEffect(), true));
    }

    private PhantomCarriage(final PhantomCarriage card) {
        super(card);
    }

    @Override
    public PhantomCarriage copy() {
        return new PhantomCarriage(this);
    }
}

class PhantomCarriageEffect extends SearchEffect {

    private static final FilterCard filter = new FilterCard("card with flashback or disturb");

    static {
        filter.add(Predicates.or(
                new AbilityPredicate(FlashbackAbility.class),
                new AbilityPredicate(DisturbAbility.class)
        ));
    }

    public PhantomCarriageEffect() {
        super(new TargetCardInLibrary(StaticFilters.FILTER_CARD_CREATURE), Outcome.Neutral);
        staticText = "search your library for a card with flashback or disturb, put it into your graveyard, then shuffle";
    }

    public PhantomCarriageEffect(final PhantomCarriageEffect effect) {
        super(effect);
    }

    @Override
    public PhantomCarriageEffect copy() {
        return new PhantomCarriageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.searchLibrary(target, source, game)) {
                if (!target.getTargets().isEmpty()) {
                    Card card = controller.getLibrary().getCard(target.getFirstTarget(), game);
                    if (card != null) {
                        controller.moveCards(card, Zone.GRAVEYARD, source, game);
                    }
                }
            }
            controller.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }

}

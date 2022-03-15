package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VelomachusLorehold extends CardImpl {

    public VelomachusLorehold(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Velomachus Lorehold attacks, look at the top seven cards of your library. You may cast an instant or sorcery spell with mana value less than or equal to Velomachus Lorehold's power from among them without paying its mana cost. Put the rest on the bottom of your library in a random order.
        this.addAbility(new AttacksTriggeredAbility(new VelomachusLoreholdEffect(), false));
    }

    private VelomachusLorehold(final VelomachusLorehold card) {
        super(card);
    }

    @Override
    public VelomachusLorehold copy() {
        return new VelomachusLorehold(this);
    }
}

class VelomachusLoreholdEffect extends OneShotEffect {

    VelomachusLoreholdEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top seven cards of your library. You may cast an instant or sorcery spell " +
                "with mana value less than or equal to {this}'s power from among them without paying its mana cost. " +
                "Put the rest on the bottom of your library in a random order";
    }

    private VelomachusLoreholdEffect(final VelomachusLoreholdEffect effect) {
        super(effect);
    }

    @Override
    public VelomachusLoreholdEffect copy() {
        return new VelomachusLoreholdEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        if (controller == null || permanent == null) {
            return false;
        }
        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 7));
        FilterCard filter = new FilterInstantOrSorceryCard();
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, permanent.getPower().getValue() + 1));
        CardUtil.castSpellWithAttributesForFree(controller, source, game, cards, filter);
        cards.retainZone(Zone.LIBRARY, game);
        controller.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}

package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KotisTheFangkeeper extends CardImpl {

    public KotisTheFangkeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // Whenever Kotis deals combat damage to a player, exile the top X cards of their library, where X is the amount of damage dealt. You may cast any number of spells with mana value X or less from among them without paying their mana costs.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new KotisTheFangkeeperEffect(), false, true
        ));
    }

    private KotisTheFangkeeper(final KotisTheFangkeeper card) {
        super(card);
    }

    @Override
    public KotisTheFangkeeper copy() {
        return new KotisTheFangkeeper(this);
    }
}

class KotisTheFangkeeperEffect extends OneShotEffect {

    KotisTheFangkeeperEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top X cards of their library, where X is the amount of damage dealt. You may " +
                "cast any number of spells with mana value X or less from among them without paying their mana costs";
    }

    private KotisTheFangkeeperEffect(final KotisTheFangkeeperEffect effect) {
        super(effect);
    }

    @Override
    public KotisTheFangkeeperEffect copy() {
        return new KotisTheFangkeeperEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        int xValue = (Integer) getValue("damage");
        if (controller == null || player == null || xValue < 1) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, xValue));
        controller.moveCards(cards, Zone.EXILED, source, game);
        FilterCard filter = new FilterCard();
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, xValue + 1));
        game.processAction();
        cards.retainZone(Zone.EXILED, game);
        CardUtil.castMultipleWithAttributeForFree(controller, source, game, cards, filter);
        return true;
    }
}

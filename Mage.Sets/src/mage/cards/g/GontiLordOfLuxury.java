package mage.cards.g;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.ExileFaceDownYouMayPlayAsLongAsExiledTargetEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class GontiLordOfLuxury extends CardImpl {

    public GontiLordOfLuxury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AETHERBORN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // When Gonti, Lord of Luxury enters the battlefield, look at the top four cards of target opponent's library, exile one of them face down,
        // then put the rest on the bottom of that library in a random order. For as long as that card remains exiled,
        // you may look at it, you may cast it, and you may spend mana as though it were mana of any type to cast it.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GontiLordOfLuxuryEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private GontiLordOfLuxury(final GontiLordOfLuxury card) {
        super(card);
    }

    @Override
    public GontiLordOfLuxury copy() {
        return new GontiLordOfLuxury(this);
    }
}

class GontiLordOfLuxuryEffect extends OneShotEffect {

    public GontiLordOfLuxuryEffect() {
        super(Outcome.Benefit);
        this.staticText = "look at the top four cards of target opponent's library, exile one of them face down, then put the rest on the bottom of that library in a random order. You may look at and cast that card for as long as it remains exiled, and you may spend mana as though it were mana of any type to cast that spell";
    }

    private GontiLordOfLuxuryEffect(final GontiLordOfLuxuryEffect effect) {
        super(effect);
    }

    @Override
    public GontiLordOfLuxuryEffect copy() {
        return new GontiLordOfLuxuryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || opponent == null || sourceObject == null) {
            return false;
        }
        Cards topCards = new CardsImpl();
        topCards.addAllCards(opponent.getLibrary().getTopCards(game, 4));
        TargetCard target = new TargetCard(Zone.LIBRARY, new FilterCard("card to exile"));
        controller.choose(outcome, topCards, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card != null) {
            new ExileFaceDownYouMayPlayAsLongAsExiledTargetEffect(true, CastManaAdjustment.AS_THOUGH_ANY_MANA_TYPE)
                    .setTargetPointer(new FixedTarget(card, game))
                    .apply(game, source);
        }
        topCards.retainZone(Zone.LIBRARY, game);
        // then put the rest on the bottom of that library in a random order
        controller.putCardsOnBottomOfLibrary(topCards, game, source, false);
        return true;
    }
}

package mage.cards.b;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileFaceDownYouMayPlayAsLongAsExiledTargetEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;
import java.util.stream.Collectors;

/**
 *
 * @author Jmlundeen
 */
public final class BlackCatCunningThief extends CardImpl {

    public BlackCatCunningThief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Black Cat enters, look at the top nine cards of target opponent's library, exile two of them face down, then put the rest on the bottom of their library in a random order. You may play the exiled cards for as long as they remain exiled. Mana of any type can be spent to cast spells this way.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BlackCatCunningThiefEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private BlackCatCunningThief(final BlackCatCunningThief card) {
        super(card);
    }

    @Override
    public BlackCatCunningThief copy() {
        return new BlackCatCunningThief(this);
    }
}
class BlackCatCunningThiefEffect extends OneShotEffect {

    BlackCatCunningThiefEffect() {
        super(Outcome.Benefit);
        this.staticText = "look at the top nine cards of target opponent's library, exile two of them face down, then put the rest on the bottom of their library in a random order. You may play the exiled cards for as long as they remain exiled. Mana of any type can be spent to cast spells this way";
    }

    private BlackCatCunningThiefEffect(final BlackCatCunningThiefEffect effect) {
        super(effect);
    }

    @Override
    public BlackCatCunningThiefEffect copy() {
        return new BlackCatCunningThiefEffect(this);
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
        topCards.addAllCards(opponent.getLibrary().getTopCards(game, 9));
        TargetCard target = new TargetCard(2, 2, Zone.LIBRARY, new FilterCard("card to exile"));
        controller.choose(outcome, topCards, target, source, game);
        Cards exiledCards = new CardsImpl(target.getTargets().stream()
                .map(game::getCard)
                .collect(Collectors.toList()));
        new ExileFaceDownYouMayPlayAsLongAsExiledTargetEffect(false, CastManaAdjustment.AS_THOUGH_ANY_MANA_TYPE)
                        .setTargetPointer(new FixedTargets(exiledCards, game))
                        .apply(game, source);
        topCards.retainZone(Zone.LIBRARY, game);
        // then put the rest on the bottom of that library in a random order
        controller.putCardsOnBottomOfLibrary(topCards, game, source, false);
        return true;
    }
}

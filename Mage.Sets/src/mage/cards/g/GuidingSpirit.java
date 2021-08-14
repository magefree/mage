package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GuidingSpirit extends CardImpl {

    public GuidingSpirit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");

        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {tap}: If the top card of target player's graveyard is a creature card, put that card on top of that player's library.
        Ability ability = new SimpleActivatedAbility(new GuidingSpiritEffect(), new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private GuidingSpirit(final GuidingSpirit card) {
        super(card);
    }

    @Override
    public GuidingSpirit copy() {
        return new GuidingSpirit(this);
    }
}

class GuidingSpiritEffect extends OneShotEffect {

    GuidingSpiritEffect() {
        super(Outcome.Benefit);
        staticText = "If the top card of target player's graveyard is a creature card, " +
                "put that card on top of that player's library.";
    }

    private GuidingSpiritEffect(final GuidingSpiritEffect effect) {
        super(effect);
    }

    @Override
    public GuidingSpiritEffect copy() {
        return new GuidingSpiritEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player == null) {
            return false;
        }
        Card card = player.getGraveyard().getTopCard(game);
        if (card != null && card.isCreature(game)) {
            player.putCardsOnTopOfLibrary(new CardsImpl(card), game, source, false);
        }
        return true;
    }
}
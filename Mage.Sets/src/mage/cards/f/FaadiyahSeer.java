
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class FaadiyahSeer extends CardImpl {

    public FaadiyahSeer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Draw a card and reveal it. If it isn't a land card, discard it.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new FaadiyahSeerEffect(), new TapSourceCost()));
    }

    public FaadiyahSeer(final FaadiyahSeer card) {
        super(card);
    }

    @Override
    public FaadiyahSeer copy() {
        return new FaadiyahSeer(this);
    }
}

class FaadiyahSeerEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterLandCard();

    public FaadiyahSeerEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Draw a card and reveal it. If it isn't a land card, discard it";
    }

    public FaadiyahSeerEffect(final FaadiyahSeerEffect effect) {
        super(effect);
    }

    @Override
    public FaadiyahSeerEffect copy() {
        return new FaadiyahSeerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = controller.getLibrary().getFromTop(game);
            controller.drawCards(1, game);
            controller.revealCards("Fa'adiyah Seer", new CardsImpl(card), game);
            if (!filter.match(card, game)) {
                controller.discard(card, source, game);
            }
            return true;
        }
        return false;
    }
}


package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class WorldShaper extends CardImpl {

    public WorldShaper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever World Shaper attacks, you may put the top three cards of your library into your graveyard.
        this.addAbility(new AttacksTriggeredAbility(new MillCardsControllerEffect(3), true));

        // When World Shaper dies, put all land cards from your graveyard onto the battlefield tapped.
        this.addAbility(new DiesSourceTriggeredAbility(new WorldShaperEffect(), false));
    }

    private WorldShaper(final WorldShaper card) {
        super(card);
    }

    @Override
    public WorldShaper copy() {
        return new WorldShaper(this);
    }
}

class WorldShaperEffect extends OneShotEffect {

    public WorldShaperEffect() {
        super(Outcome.PutLandInPlay);
        this.staticText = "return all land cards from your graveyard to the battlefield tapped";
    }

    public WorldShaperEffect(final WorldShaperEffect effect) {
        super(effect);
    }

    @Override
    public WorldShaperEffect copy() {
        return new WorldShaperEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.moveCards(controller.getGraveyard().getCards(StaticFilters.FILTER_CARD_LAND, game),
                    Zone.BATTLEFIELD, source, game, true, false, false, null); // owner param should play no role here
            return true;
        }
        return false;
    }
}

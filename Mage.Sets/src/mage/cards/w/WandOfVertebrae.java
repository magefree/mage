package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.ShuffleIntoLibraryTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author TheElk801
 */
public final class WandOfVertebrae extends CardImpl {

    public WandOfVertebrae(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {T}: Put the top card of your library into your graveyard.
        this.addAbility(new SimpleActivatedAbility(
                new MillCardsControllerEffect(1),
                new TapSourceCost()
        ));

        // {2}, {T}, Exile Wand of Vertebrae: Shuffle up to five target cards from your graveyard into your library.
        Ability ability = new SimpleActivatedAbility(new ShuffleIntoLibraryTargetEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard(0, 5));
        this.addAbility(ability);
    }

    private WandOfVertebrae(final WandOfVertebrae card) {
        super(card);
    }

    @Override
    public WandOfVertebrae copy() {
        return new WandOfVertebrae(this);
    }
}
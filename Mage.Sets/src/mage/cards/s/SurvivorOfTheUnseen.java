
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.constants.SubType;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author TheElk801
 */
public final class SurvivorOfTheUnseen extends CardImpl {

    public SurvivorOfTheUnseen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Cumulative upkeep {2}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl("{2}")));

        // {T}: Draw two cards, then put a card from your hand on top of your library.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new SurvivorOfTheUnseenEffect(), new TapSourceCost()));
    }

    private SurvivorOfTheUnseen(final SurvivorOfTheUnseen card) {
        super(card);
    }

    @Override
    public SurvivorOfTheUnseen copy() {
        return new SurvivorOfTheUnseen(this);
    }
}

class SurvivorOfTheUnseenEffect extends OneShotEffect {

    public SurvivorOfTheUnseenEffect() {
        super(Outcome.DrawCard);
        staticText = "Draw two cards, then put a card from your hand on top of your library";
    }

    public SurvivorOfTheUnseenEffect(final SurvivorOfTheUnseenEffect effect) {
        super(effect);
    }

    @Override
    public SurvivorOfTheUnseenEffect copy() {
        return new SurvivorOfTheUnseenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.drawCards(2, source, game);
            putOnLibrary(player, source, game);
            return true;
        }
        return false;
    }

    private boolean putOnLibrary(Player player, Ability source, Game game) {
        TargetCardInHand target = new TargetCardInHand();
        if (target.canChoose(player.getId(), source, game)) {
            player.chooseTarget(Outcome.ReturnToHand, target, source, game);
            Card card = player.getHand().get(target.getFirstTarget(), game);
            if (card != null) {
                return player.moveCardToLibraryWithInfo(card, source, game, Zone.HAND, true, false);
            }
        }
        return false;
    }
}

package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AudaciousReshapers extends CardImpl {

    public AudaciousReshapers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {T}, Sacrifice an artifact: Reveal cards from the top of your library until you reveal an artifact card. Put that card onto the battlefield and the rest on the bottom of your library in a random order. Audacious Reshapers deals damage to you equal to the number of cards revealed this way.
        Ability ability = new SimpleActivatedAbility(new AudaciousReshapersEffect(), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_AN
        )));
        this.addAbility(ability);
    }

    private AudaciousReshapers(final AudaciousReshapers card) {
        super(card);
    }

    @Override
    public AudaciousReshapers copy() {
        return new AudaciousReshapers(this);
    }
}

class AudaciousReshapersEffect extends OneShotEffect {

    AudaciousReshapersEffect() {
        super(Outcome.Benefit);
        staticText = "reveal cards from the top of your library until you reveal an artifact card. " +
                "Put that card onto the battlefield and the rest on the bottom of your library in a random order. " +
                "{this} deals damage to you equal to the number of cards revealed this way";
    }

    private AudaciousReshapersEffect(final AudaciousReshapersEffect effect) {
        super(effect);
    }

    @Override
    public AudaciousReshapersEffect copy() {
        return new AudaciousReshapersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        Card artifact = null;
        for (Card card : player.getLibrary().getCards(game)) {
            if (card == null) {
                continue;
            }
            cards.add(card);
            if (card.isArtifact(game)) {
                artifact = card;
                break;
            }
        }
        int size = cards.size();
        player.revealCards(source, cards, game);
        if (artifact != null) {
            player.moveCards(artifact, Zone.BATTLEFIELD, source, game);
        }
        cards.removeIf(uuid -> game.getState().getZone(uuid) != Zone.LIBRARY);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        player.damage(size, source.getSourceId(), source, game);
        return true;
    }
}

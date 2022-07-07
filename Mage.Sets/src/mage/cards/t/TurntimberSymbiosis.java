package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TurntimberSymbiosis extends ModalDoubleFacesCard {

    public TurntimberSymbiosis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.SORCERY}, new SubType[]{}, "{4}{G}{G}{G}",
                "Turntimber, Serpentine Wood", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Turntimber Symbiosis
        // Sorcery

        // Look at the top seven cards of your library. You may put a creature card from among them onto the battlefield.
        // If that card has converted mana cost 3 or less, it enters with three additional +1/+1 counters on it.
        // Put the rest on the bottom of your library in a random order.
        this.getLeftHalfCard().getSpellAbility().addEffect(new TurntimberSymbiosisEffect());

        // 2.
        // Turntimber, Serpentine Wood
        // Land

        // As Turntimber, Serpentine Wood enters the battlefield, you may pay 3 life. If you don't, it enters the battlefield tapped.
        this.getRightHalfCard().addAbility(new AsEntersBattlefieldAbility(
                new TapSourceUnlessPaysEffect(new PayLifeCost(3)),
                "you may pay 3 life. If you don't, it enters the battlefield tapped"
        ));

        // {T}: Add {G}.
        this.getRightHalfCard().addAbility(new GreenManaAbility());
    }

    private TurntimberSymbiosis(final TurntimberSymbiosis card) {
        super(card);
    }

    @Override
    public TurntimberSymbiosis copy() {
        return new TurntimberSymbiosis(this);
    }
}

class TurntimberSymbiosisEffect extends OneShotEffect {

    TurntimberSymbiosisEffect() {
        super(Outcome.Benefit);
        staticText = "Look at the top seven cards of your library. You may put a creature card " +
                "from among them onto the battlefield. If that card has mana value 3 or less, " +
                "it enters with three additional +1/+1 counters on it. " +
                "Put the rest on the bottom of your library in a random order";
    }

    private TurntimberSymbiosisEffect(final TurntimberSymbiosisEffect effect) {
        super(effect);
    }

    @Override
    public TurntimberSymbiosisEffect copy() {
        return new TurntimberSymbiosisEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 7));
        TargetCard target = new TargetCardInLibrary(
                0, 1, StaticFilters.FILTER_CARD_CREATURE
        );
        player.choose(outcome, cards, target, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card != null) {
            cards.remove(card);

            boolean small = card.getManaValue() <= 3;
            player.moveCards(card, Zone.BATTLEFIELD, source, game);
            Permanent permanent = game.getPermanent(card.getId());
            if (small && permanent != null) {
                permanent.addCounters(CounterType.P1P1.createInstance(3), source.getControllerId(), source, game);
            }
        }
        player.putCardsOnBottomOfLibrary(cards, game, source, false);

        return true;
    }
}

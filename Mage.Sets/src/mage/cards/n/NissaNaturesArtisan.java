package mage.cards.n;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.players.Player;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author fireshoes
 */
public final class NissaNaturesArtisan extends CardImpl {

    public NissaNaturesArtisan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{G}{G}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.NISSA);

        this.setStartingLoyalty(5);

        // +3: You gain 3 life.
        this.addAbility(new LoyaltyAbility(new GainLifeEffect(3), 3));

        // -4: Reveal the top two cards of your library. Put all land cards from among 
        // them onto the battlefield and the rest into your hand.
        this.addAbility(new LoyaltyAbility(new NissaNaturesArtisanEffect(), -4));

        // -12: Creatures you control get +5/+5 and gain trample until end of turn.
        LoyaltyAbility ability = new LoyaltyAbility(new BoostControlledEffect(
                5, 5, Duration.EndOfTurn
        ).setText("creatures you control get +5/+5"), -12);
        ability.addEffect(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gain trample until end of turn"));
        this.addAbility(ability);
    }

    private NissaNaturesArtisan(final NissaNaturesArtisan card) {
        super(card);
    }

    @Override
    public NissaNaturesArtisan copy() {
        return new NissaNaturesArtisan(this);
    }
}

class NissaNaturesArtisanEffect extends OneShotEffect {

    public NissaNaturesArtisanEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "Reveal the top two cards of your library. Put all land cards "
                + "from among them onto the battlefield and the rest into your hand";
    }

    public NissaNaturesArtisanEffect(final NissaNaturesArtisanEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller == null || sourceObject == null) {
            return false;
        }
        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 2));
        if (!cards.isEmpty()) {
            controller.revealCards(sourceObject.getIdName(), cards, game);
            Set<Card> toBattlefield = new LinkedHashSet<>();
            for (Card card : cards.getCards(new FilterLandCard(),
                    source.getSourceId(), source.getControllerId(), game)) {
                cards.remove(card);
                toBattlefield.add(card);
            }
            controller.moveCards(toBattlefield, Zone.BATTLEFIELD, source,
                    game, false, false, true, null);
            controller.moveCards(cards, Zone.HAND, source, game);
        }
        return true;
    }

    @Override
    public NissaNaturesArtisanEffect copy() {
        return new NissaNaturesArtisanEffect(this);
    }

}

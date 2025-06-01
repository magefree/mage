package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChocoSeekerOfParadise extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.BIRD, "Birds you control");

    public ChocoSeekerOfParadise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Whenever one or more Birds you control attack, look at that many cards from the top of your library. You may put one of them into your hand. Then put any number of land cards from among them onto the battlefield tapped and the rest into your graveyard.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
                new ChocoSeekerOfParadiseEffect(), 1, filter
        ).setTriggerPhrase("Whenever one or more Birds you control attack, "));

        // Landfall -- Whenever a land you control enters, Choco gets +1/+0 until end of turn.
        this.addAbility(new LandfallAbility(new BoostSourceEffect(1, 0, Duration.EndOfTurn)));
    }

    private ChocoSeekerOfParadise(final ChocoSeekerOfParadise card) {
        super(card);
    }

    @Override
    public ChocoSeekerOfParadise copy() {
        return new ChocoSeekerOfParadise(this);
    }
}

class ChocoSeekerOfParadiseEffect extends OneShotEffect {

    ChocoSeekerOfParadiseEffect() {
        super(Outcome.Benefit);
        staticText = "look at that many cards from the top of your library. " +
                "You may put one of them into your hand. Then put any number of land cards " +
                "from among them onto the battlefield tapped and the rest into your graveyard";
    }

    private ChocoSeekerOfParadiseEffect(final ChocoSeekerOfParadiseEffect effect) {
        super(effect);
    }

    @Override
    public ChocoSeekerOfParadiseEffect copy() {
        return new ChocoSeekerOfParadiseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int amount = (Integer) getValue(AttacksWithCreaturesTriggeredAbility.VALUEKEY_NUMBER_ATTACKERS);
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, amount));
        cards.retainZone(Zone.LIBRARY, game);
        if (cards.isEmpty()) {
            return false;
        }
        TargetCard target = new TargetCardInLibrary(0, 1, StaticFilters.FILTER_CARD);
        target.withChooseHint("to put into your hand");
        player.choose(outcome, cards, target, source, game);
        Optional.ofNullable(target.getFirstTarget())
                .map(game::getCard)
                .ifPresent(card -> player.moveCards(card, Zone.HAND, source, game));
        cards.retainZone(Zone.LIBRARY, game);
        target = new TargetCardInLibrary(0, Integer.MAX_VALUE, StaticFilters.FILTER_CARD_LANDS);
        target.withChooseHint("to put onto the battlefield tapped");
        player.choose(outcome, cards, target, source, game);
        player.moveCards(
                new CardsImpl(target.getTargets()).getCards(game), Zone.BATTLEFIELD, source,
                game, true, false, false, null
        );
        cards.retainZone(Zone.LIBRARY, game);
        player.moveCards(cards, Zone.GRAVEYARD, source, game);
        return true;
    }
}

package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.*;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EtaliPrimalConqueror extends TransformingDoubleFacedCard {

    public EtaliPrimalConqueror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELDER, SubType.DINOSAUR}, "{5}{R}{R}",
                "Etali, Primal Sickness",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.PHYREXIAN, SubType.ELDER, SubType.DINOSAUR}, "RG"
        );

        // Etali, Primal Conqueror
        this.getLeftHalfCard().setPT(7, 7);

        // Trample
        this.getLeftHalfCard().addAbility(TrampleAbility.getInstance());

        // When Etali, Primal Conqueror enters the battlefield, each player exiles cards from the top of their library until they exile a nonland card. You may cast any number of spells from among the nonland cards exiled this way without paying their mana costs.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new EtaliPrimalConquerorEffect()));

        // {9}{G/P}: Transform Etali. Activate only as a sorcery.
        this.getLeftHalfCard().addAbility(new ActivateAsSorceryActivatedAbility(new TransformSourceEffect(), new ManaCostsImpl<>("{9}{G/P}")));

        // Etali, Primal Sickness
        this.getRightHalfCard().setPT(11, 11);

        // Trample
        this.getRightHalfCard().addAbility(TrampleAbility.getInstance());

        // Indestructible
        this.getRightHalfCard().addAbility(IndestructibleAbility.getInstance());

        // Whenever Etali, Primal Sickness deals combat damage to a player, they get that many poison counters.
        this.getRightHalfCard().addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new EtaliPrimalSicknessEffect(), false, true
        ));
    }

    private EtaliPrimalConqueror(final EtaliPrimalConqueror card) {
        super(card);
    }

    @Override
    public EtaliPrimalConqueror copy() {
        return new EtaliPrimalConqueror(this);
    }
}

class EtaliPrimalConquerorEffect extends OneShotEffect {

    EtaliPrimalConquerorEffect() {
        super(Outcome.Benefit);
        staticText = "each player exiles cards from the top of their library until they exile a nonland card. " +
                "You may cast any number of spells from among the nonland cards exiled this way without paying their mana costs";
    }

    private EtaliPrimalConquerorEffect(final EtaliPrimalConquerorEffect effect) {
        super(effect);
    }

    @Override
    public EtaliPrimalConquerorEffect copy() {
        return new EtaliPrimalConquerorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            for (Card card : player.getLibrary().getCards(game)) {
                player.moveCards(card, Zone.EXILED, source, game);
                if (!card.isLand(game)) {
                    cards.add(card);
                    break;
                }
            }
        }
        game.processAction();
        cards.retainZone(Zone.EXILED, game);
        CardUtil.castMultipleWithAttributeForFree(controller, source, game, cards, StaticFilters.FILTER_CARD);
        return true;
    }
}

class EtaliPrimalSicknessEffect extends OneShotEffect {

    EtaliPrimalSicknessEffect() {
        super(Outcome.Benefit);
        staticText = "they get that many poison counters";
    }

    private EtaliPrimalSicknessEffect(final EtaliPrimalSicknessEffect effect) {
        super(effect);
    }

    @Override
    public EtaliPrimalSicknessEffect copy() {
        return new EtaliPrimalSicknessEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        int damage = (Integer) getValue("damage");
        return player != null && player.addCounters(CounterType.POISON.createInstance(damage), source.getControllerId(), source, game);
    }
}

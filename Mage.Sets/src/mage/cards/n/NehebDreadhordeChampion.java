package mage.cards.n;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NehebDreadhordeChampion extends CardImpl {

    public NehebDreadhordeChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Neheb, Dreadhorde Champion deals combat damage to a player or planeswalker, you may discard any number of cards. If you do, draw that many cards and add that much {R}. Until end of turn, you don't lose this mana as steps and phases end.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new NehebDreadhordeChampionEffect(), true).setOrPlaneswalker(true)
        );
    }

    private NehebDreadhordeChampion(final NehebDreadhordeChampion card) {
        super(card);
    }

    @Override
    public NehebDreadhordeChampion copy() {
        return new NehebDreadhordeChampion(this);
    }
}

class NehebDreadhordeChampionEffect extends OneShotEffect {

    NehebDreadhordeChampionEffect() {
        super(Outcome.Benefit);
        staticText = "discard any number of cards. If you do, draw that many cards and add that much {R}. " +
                "Until end of turn, you don't lose this mana as steps and phases end.";
    }

    private NehebDreadhordeChampionEffect(final NehebDreadhordeChampionEffect effect) {
        super(effect);
    }

    @Override
    public NehebDreadhordeChampionEffect copy() {
        return new NehebDreadhordeChampionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCard target = new TargetCardInHand(0, Integer.MAX_VALUE, StaticFilters.FILTER_CARD);
        if (!player.choose(outcome, target, source.getSourceId(), game)) {
            return false;
        }
        Cards cards = new CardsImpl(target.getTargets());
        int counter = 0;
        Mana mana = new Mana();
        for (Card card : cards.getCards(game)) {
            if (player.discard(card, source, game)) {
                counter++;
                mana.increaseRed();
            }
        }
        if (counter == 0) {
            return true;
        }
        player.drawCards(counter, game);
        player.getManaPool().addMana(mana, game, source, true);
        return true;
    }
}
package mage.cards.n;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerOrPlaneswalkerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NehebDreadhordeChampion extends CardImpl {

    public NehebDreadhordeChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Neheb, Dreadhorde Champion deals combat damage to a player or planeswalker, you may discard any number of cards. If you do, draw that many cards and add that much {R}. Until end of turn, you don't lose this mana as steps and phases end.
        this.addAbility(new DealsCombatDamageToAPlayerOrPlaneswalkerTriggeredAbility(new NehebDreadhordeChampionEffect(), true));
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
        int counter = player.discard(0, Integer.MAX_VALUE, false, source, game).size();
        if (counter < 1) {
            return true;
        }
        player.drawCards(counter, source, game);
        player.getManaPool().addMana(new Mana(ManaType.RED, counter), game, source, true);
        return true;
    }
}

package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EtaliPrimalSickness extends CardImpl {

    public EtaliPrimalSickness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(11);
        this.toughness = new MageInt(11);
        this.color.setRed(true);
        this.color.setGreen(true);
        this.nightCard = true;

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // Whenever Etali, Primal Sickness deals combat damage to a player, they get that many poison counters.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new EtaliPrimalSicknessEffect(), false, true
        ));
    }

    private EtaliPrimalSickness(final EtaliPrimalSickness card) {
        super(card);
    }

    @Override
    public EtaliPrimalSickness copy() {
        return new EtaliPrimalSickness(this);
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
        return player.addCounters(CounterType.POISON.createInstance(damage), source.getControllerId(), source, game);
    }
}

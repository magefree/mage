package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.LivingMetalAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FlamewarStreetwiseOperative extends CardImpl {

    public FlamewarStreetwiseOperative(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.color.setRed(true);
        this.color.setBlack(true);
        this.nightCard = true;

        // Living metal
        this.addAbility(new LivingMetalAbility());

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever Flamewar deals combat damage to a player, exile that many cards from the top of your library face down. Put an intel counter on each of them. Convert Flamewar.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new FlamewarBrashVeteranEffect(), false);
        ability.addEffect(new TransformSourceEffect().setText("convert {this}"));
        this.addAbility(ability);
    }

    private FlamewarStreetwiseOperative(final FlamewarStreetwiseOperative card) {
        super(card);
    }

    @Override
    public FlamewarStreetwiseOperative copy() {
        return new FlamewarStreetwiseOperative(this);
    }
}

class FlamewarStreetwiseOperativeEffect extends OneShotEffect {

    FlamewarStreetwiseOperativeEffect() {
        super(Outcome.Benefit);
        staticText = "exile that many cards from the top of your library face down. " +
                "Put an intel counter on each of them";
    }

    private FlamewarStreetwiseOperativeEffect(final FlamewarStreetwiseOperativeEffect effect) {
        super(effect);
    }

    @Override
    public FlamewarStreetwiseOperativeEffect copy() {
        return new FlamewarStreetwiseOperativeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        int damage = (Integer) getValue("damage");
        if (player == null || damage < 1) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, damage));
        player.moveCards(cards, Zone.EXILED, source, game);
        cards.retainZone(Zone.EXILED, game);
        cards.getCards(game).stream().forEach(card -> {
            card.setFaceDown(true, game);
            card.addCounters(CounterType.INTEL.createInstance(), source, game);
        });
        return true;
    }
}

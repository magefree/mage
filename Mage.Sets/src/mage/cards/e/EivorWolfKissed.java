package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardAndOrCard;

import java.util.UUID;

/**
 * @author TheElk801, xenohedron
 */
public final class EivorWolfKissed extends CardImpl {

    public EivorWolfKissed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(7);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Eivor, Wolf-Kissed deals combat damage to a player, you mill that many cards. You may put a Saga card and/or a land card from among them onto the battlefield.
        this.addAbility(new DealsCombatDamageTriggeredAbility(new EivorWolfKissedEffect(), false));
    }

    private EivorWolfKissed(final EivorWolfKissed card) {
        super(card);
    }

    @Override
    public EivorWolfKissed copy() {
        return new EivorWolfKissed(this);
    }
}

class EivorWolfKissedEffect extends OneShotEffect {

    EivorWolfKissedEffect() {
        super(Outcome.Benefit);
        staticText = "you mill that many cards. You may put a Saga card " +
                "and/or a land card from among them onto the battlefield";
    }

    private EivorWolfKissedEffect(final EivorWolfKissedEffect effect) {
        super(effect);
    }

    @Override
    public EivorWolfKissedEffect copy() {
        return new EivorWolfKissedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        int damage = (Integer) getValue("damage");
        if (player == null || damage < 1) {
            return false;
        }
        Cards cards = player.millCards(damage, source, game);
        TargetCard target = new TargetCardAndOrCard(SubType.SAGA.getPredicate(),
                CardType.LAND.getPredicate(),
                "a Saga card and/or a land card");
        player.choose(outcome, cards, target, source, game);
        player.moveCards(new CardsImpl(target.getTargets()), Zone.BATTLEFIELD, source, game);
        return true;
    }
}

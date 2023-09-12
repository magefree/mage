package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoUnlessControllerPaysEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.constants.SubType;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public final class Megatherium extends CardImpl {

    public Megatherium(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Megatherium enters the battlefield, sacrifice it unless you pay {1} for each card in your hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MegatheriumEffect(), false));
    }

    private Megatherium(final Megatherium card) {
        super(card);
    }

    @Override
    public Megatherium copy() {
        return new Megatherium(this);
    }
}

class MegatheriumEffect extends OneShotEffect {

    public MegatheriumEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "sacrifice it unless you pay {1} for each card in your hand";
    }

    private MegatheriumEffect(final MegatheriumEffect effect) {
        super(effect);
    }

    @Override
    public MegatheriumEffect copy() {
        return new MegatheriumEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        return new DoUnlessControllerPaysEffect(
                new SacrificeSourceEffect(),
                new GenericManaCost(player.getHand().size())
        ).apply(game, source);
    }
}

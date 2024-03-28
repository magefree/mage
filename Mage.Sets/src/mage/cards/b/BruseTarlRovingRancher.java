package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.token.Ox22Token;
import mage.players.Library;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class BruseTarlRovingRancher extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.OX, "Oxen");

    public BruseTarlRovingRancher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Oxen you control have double strike.
        this.addAbility(new SimpleStaticAbility(
                new GainAbilityControlledEffect(
                        DoubleStrikeAbility.getInstance(),
                        Duration.WhileOnBattlefield, filter
                )
        ));

        // Whenever Bruse Tarl, Roving Rancher enters the battlefield or attacks, exile the top card of your library. If it's a land card, create a 2/2 white Ox creature token. Otherwise, you may cast it until the end of your next turn.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new BruseTarlRovingRancherEffect()));
    }

    private BruseTarlRovingRancher(final BruseTarlRovingRancher card) {
        super(card);
    }

    @Override
    public BruseTarlRovingRancher copy() {
        return new BruseTarlRovingRancher(this);
    }
}

class BruseTarlRovingRancherEffect extends OneShotEffect {

    BruseTarlRovingRancherEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top card of your library. "
                + "If it's a land card, create a 2/2 white Ox creature token. "
                + "Otherwise, you may cast it until the end of your next turn.";
    }

    private BruseTarlRovingRancherEffect(final BruseTarlRovingRancherEffect effect) {
        super(effect);
    }

    @Override
    public BruseTarlRovingRancherEffect copy() {
        return new BruseTarlRovingRancherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Library library = player.getLibrary();
        if (library == null || !library.hasCards()) {
            return false;
        }
        Card card = library.getFromTop(game);
        if (card == null) {
            return false;
        }

        if (card.isLand(game)) {
            player.moveCards(card, Zone.EXILED, source, game);
            new CreateTokenEffect(new Ox22Token()).apply(game, source);
        } else {
            PlayFromNotOwnHandZoneTargetEffect.exileAndPlayFromExile(
                    game, source, card, TargetController.YOU,
                    Duration.UntilEndOfYourNextTurn,
                    false, false, true
            );
        }
        return true;
    }

}
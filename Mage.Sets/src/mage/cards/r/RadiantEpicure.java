package mage.cards.r;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;
import mage.watchers.common.ManaSpentToCastWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RadiantEpicure extends CardImpl {

    public RadiantEpicure(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Converge — When Radiant Epicure enters the battlefield, each opponent loses X life and you gain X life, where X is the number of colors of mana spent to cast this spell.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new RadiantEpicureEffect(), false)
                .withFlavorWord("Converge")
        );
    }

    private RadiantEpicure(final RadiantEpicure card) {
        super(card);
    }

    @Override
    public RadiantEpicure copy() {
        return new RadiantEpicure(this);
    }
}

class RadiantEpicureEffect extends OneShotEffect {

    RadiantEpicureEffect() {
        super(Outcome.Benefit);
        staticText = "each opponent loses X life and you gain X life, " +
                "where X is the number of colors of mana spent to cast this spell";
    }

    private RadiantEpicureEffect(final RadiantEpicureEffect effect) {
        super(effect);
    }

    @Override
    public RadiantEpicureEffect copy() {
        return new RadiantEpicureEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        ManaSpentToCastWatcher watcher = game.getState().getWatcher(ManaSpentToCastWatcher.class);
        if (player == null || watcher == null) {
            return false;
        }
        Mana payment = watcher.getLastManaPayment(source.getSourceId());
        if (payment == null) {
            return false;
        }
        int xValue = payment.getDifferentColors();
        new DamagePlayersEffect(xValue, TargetController.OPPONENT).apply(game, source);
        player.gainLife(xValue, game, source);
        return true;
    }
}

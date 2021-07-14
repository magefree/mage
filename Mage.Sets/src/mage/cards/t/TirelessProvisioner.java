package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.token.FoodToken;
import mage.game.permanent.token.Token;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TirelessProvisioner extends CardImpl {

    public TirelessProvisioner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Landfall — Whenever a land enters the battelfield under your control, create a Food token or a Treasure token.
        this.addAbility(new LandfallAbility(new TirelessProvisionerEffect()));
    }

    private TirelessProvisioner(final TirelessProvisioner card) {
        super(card);
    }

    @Override
    public TirelessProvisioner copy() {
        return new TirelessProvisioner(this);
    }
}

class TirelessProvisionerEffect extends OneShotEffect {

    TirelessProvisionerEffect() {
        super(Outcome.Benefit);
        staticText = "create a Food token or a Treasure token";
    }

    private TirelessProvisionerEffect(final TirelessProvisionerEffect effect) {
        super(effect);
    }

    @Override
    public TirelessProvisionerEffect copy() {
        return new TirelessProvisionerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Token token = player.chooseUse(
                outcome, "Create a Food token or a Treasure token?",
                null, "Food", "Treasure", source, game
        ) ? new FoodToken() : new TreasureToken();
        return token.putOntoBattlefield(1, game, source, source.getControllerId());
    }
}

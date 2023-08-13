
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.LandMineToken;
import mage.players.Player;

/**
 *
 * @author Quercitron
 */
public final class GoblinKaboomist extends CardImpl {

    public GoblinKaboomist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // At the beginning of your upkeep, create a colorless artifact token named Land Mine
        // with "{R}, Sacrifice this artifact: This artifact deals 2 damage to target attacking creature without flying."
        // Then flip a coin.  If you lose the flip, Goblin Kaboomist deals 2 damage to itself.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new CreateTokenEffect(new LandMineToken()), TargetController.YOU, false);
        ability.addEffect(new GoblinKaboomistFlipCoinEffect());
        this.addAbility(ability);
    }

    private GoblinKaboomist(final GoblinKaboomist card) {
        super(card);
    }

    @Override
    public GoblinKaboomist copy() {
        return new GoblinKaboomist(this);
    }
}

class GoblinKaboomistFlipCoinEffect extends OneShotEffect {

    public GoblinKaboomistFlipCoinEffect() {
        super(Outcome.Damage);
        staticText = "Then flip a coin. If you lose the flip, {this} deals 2 damage to itself";
    }

    public GoblinKaboomistFlipCoinEffect(final GoblinKaboomistFlipCoinEffect effect) {
        super(effect);
    }

    @Override
    public GoblinKaboomistFlipCoinEffect copy() {
        return new GoblinKaboomistFlipCoinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player != null && permanent != null) {
            if (!player.flipCoin(source, game, true)) {
                String message = permanent.getLogName() + " deals 2 damage to itself";
                game.informPlayers(message);
                permanent.damage(2, source.getSourceId(), source, game, false, true);
            }
            return true;
        }
        return false;
    }

}

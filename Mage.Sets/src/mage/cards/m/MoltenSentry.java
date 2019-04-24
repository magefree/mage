
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class MoltenSentry extends CardImpl {

    private final static String rule = "As {this} enters the battlefield, flip a coin. If the coin comes up heads, {this} enters the battlefield as a "
            + "5/2 creature with haste. If it comes up tails, {this} enters the battlefield as a 2/5 creature with defender.";

    public MoltenSentry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // As Molten Sentry enters the battlefield, flip a coin. If the coin comes up heads, Molten Sentry enters the battlefield as a 5/2 creature with haste.
        // If it comes up tails, Molten Sentry enters the battlefield as a 2/5 creature with defender.
        this.addAbility(new EntersBattlefieldAbility(new MoltenSentryEffect(), null, rule, ""));
    }

    public MoltenSentry(final MoltenSentry card) {
        super(card);
    }

    @Override
    public MoltenSentry copy() {
        return new MoltenSentry(this);
    }
}

class MoltenSentryEffect extends OneShotEffect {

    public MoltenSentryEffect() {
        super(Outcome.Damage);
    }

    public MoltenSentryEffect(MoltenSentryEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        if (controller != null && permanent != null) {
            if (controller.flipCoin(game)) {
                game.informPlayers("Heads: " + permanent.getLogName() + " enters the battlefield as a 5/2 creature with haste");
                permanent.getPower().modifyBaseValue(5);
                permanent.getToughness().modifyBaseValue(2);
                game.addEffect(new GainAbilitySourceEffect(HasteAbility.getInstance(), Duration.WhileOnBattlefield), source);
                return true;
            } else {
                game.informPlayers("Tails: " + permanent.getLogName() + " enters the battlefield as a 2/5 creature with defender");
                permanent.getPower().modifyBaseValue(2);
                permanent.getToughness().modifyBaseValue(5);
                game.addEffect(new GainAbilitySourceEffect(DefenderAbility.getInstance(), Duration.WhileOnBattlefield), source);
                return true;
            }
        }
        return false;
    }

    @Override
    public MoltenSentryEffect copy() {
        return new MoltenSentryEffect(this);
    }
}

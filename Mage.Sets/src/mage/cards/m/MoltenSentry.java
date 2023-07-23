package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class MoltenSentry extends CardImpl {

    public MoltenSentry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // As Molten Sentry enters the battlefield, flip a coin. If the coin comes up heads, Molten Sentry enters the battlefield as a 5/2 creature with haste.
        // If it comes up tails, Molten Sentry enters the battlefield as a 2/5 creature with defender.
        this.addAbility(new AsEntersBattlefieldAbility(new MoltenSentryEffect()));
    }

    private MoltenSentry(final MoltenSentry card) {
        super(card);
    }

    @Override
    public MoltenSentry copy() {
        return new MoltenSentry(this);
    }
}

class MoltenSentryEffect extends OneShotEffect {

    MoltenSentryEffect() {
        super(Outcome.Benefit);
        staticText = "flip a coin. If the coin comes up heads, {this} enters the battlefield as a 5/2 creature with haste. " +
                "If it comes up tails, {this} enters the battlefield as a 2/5 creature with defender";
    }

    private MoltenSentryEffect(MoltenSentryEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        if (controller == null || permanent == null) {
            return false;
        }

        int power;
        int toughness;
        Ability gainedAbility;
        /* TODO: The chosen characteristics are copiable values; make sure this is handled correctly
         * 707.2. When copying an object, the copy acquires the copiable values of the original object's characteristics...
         * The copiable values are the values derived from the text printed on the object
         * (that text being name, mana cost, color indicator, card type, subtype, supertype, rules text, power, toughness, and/or loyalty),
         * as modified by other copy effects, by its face-down status,
         * and by "as ... enters the battlefield" and "as ... is turned face up" abilities
         * that set power and toughness (and may also set additional characteristics).
         */
        if (controller.flipCoin(source, game, false)) {
            game.informPlayers("Heads: " + permanent.getLogName() + " enters the battlefield as a 5/2 creature with haste");
            power = 5;
            toughness = 2;
            gainedAbility = HasteAbility.getInstance();
        } else {
            game.informPlayers("Tails: " + permanent.getLogName() + " enters the battlefield as a 2/5 creature with defender");
            power = 2;
            toughness = 5;
            gainedAbility = DefenderAbility.getInstance();
        }
        game.addEffect(new SetBasePowerToughnessSourceEffect(power, toughness, Duration.WhileOnBattlefield, SubLayer.SetPT_7b), source);
        game.addEffect(new GainAbilitySourceEffect(gainedAbility, Duration.WhileOnBattlefield), source);
        return true;
    }

    @Override
    public MoltenSentryEffect copy() {
        return new MoltenSentryEffect(this);
    }
}

package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SetCopiableCharacteristicsSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.game.permanent.token.TokenImpl;
import mage.players.Player;

import java.util.UUID;

/**
 * @author xenohedron
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
        super(Outcome.AddAbility);
        staticText = "flip a coin. If the coin comes up heads, {this} enters the battlefield as a 5/2 creature with haste." +
                " If it comes up tails, {this} enters the battlefield as a 2/5 creature with defender.";
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

        Token characteristics;
        if (controller.flipCoin(source, game, false)) {
            game.informPlayers("Heads: " + permanent.getLogName() + " enters the battlefield as a 5/2 creature with haste");
            characteristics = new MoltenSentry52Token();
        } else {
            game.informPlayers("Tails: " + permanent.getLogName() + " enters the battlefield as a 2/5 creature with defender");
            characteristics = new MoltenSentry25Token();
        }
        game.addEffect(new SetCopiableCharacteristicsSourceEffect(characteristics), source);
        return true;
    }

    @Override
    public MoltenSentryEffect copy() {
        return new MoltenSentryEffect(this);
    }
}

class MoltenSentry52Token extends TokenImpl {

    MoltenSentry52Token() {
        super("", "5/2 creature with haste");
        cardType.add(CardType.CREATURE);
        power = new MageInt(5);
        toughness = new MageInt(2);
        this.addAbility(HasteAbility.getInstance());
    }

    private MoltenSentry52Token(final MoltenSentry52Token token) {
        super(token);
    }

    public MoltenSentry52Token copy() {
        return new MoltenSentry52Token(this);
    }
}

class MoltenSentry25Token extends TokenImpl {

    MoltenSentry25Token() {
        super("", "2/5 creature with defender");
        cardType.add(CardType.CREATURE);
        power = new MageInt(2);
        toughness = new MageInt(5);
        this.addAbility(DefenderAbility.getInstance());
    }

    private MoltenSentry25Token(final MoltenSentry25Token token) {
        super(token);
    }

    public MoltenSentry25Token copy() {
        return new MoltenSentry25Token(this);
    }
}

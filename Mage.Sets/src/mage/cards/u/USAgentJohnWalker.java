package mage.cards.u;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SturdyShieldToken;
import mage.game.permanent.token.Token;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class USAgentJohnWalker extends CardImpl {

    public USAgentJohnWalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W/B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When U.S.Agent enters, create a colorless Equipment artifact token named Sturdy Shield with
        // "Equipped creature gets +1/+2" and equip {2}. Attach it to U.S.Agent.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new USAgentJohnWalkerEffect()));
    }

    private USAgentJohnWalker(final USAgentJohnWalker card) {
        super(card);
    }

    @Override
    public USAgentJohnWalker copy() {
        return new USAgentJohnWalker(this);
    }
}

class USAgentJohnWalkerEffect extends OneShotEffect {

    USAgentJohnWalkerEffect() {
        super(Outcome.BoostCreature);
        staticText = "create a colorless Equipment artifact token named Sturdy Shield with " +
                "\"Equipped creature gets +1/+2\" and equip {2}. Attach it to {this}";
    }

    private USAgentJohnWalkerEffect(final USAgentJohnWalkerEffect effect) {
        super(effect);
    }

    @Override
    public USAgentJohnWalkerEffect copy() {
        return new USAgentJohnWalkerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new SturdyShieldToken();
        token.putOntoBattlefield(1, game, source);
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        if (sourcePermanent == null) {
            return false;
        }

        for (UUID tokenId : token.getLastAddedTokenIds()) {
            Permanent tokenPermanent = game.getPermanent(tokenId);
            if (tokenPermanent != null) {
                tokenPermanent.addAttachment(sourcePermanent.getId(), source, game);
            }
        }
        return true;
    }
}

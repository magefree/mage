
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.GeminiEngineTwinToken;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class GeminiEngine extends CardImpl {

    public GeminiEngine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever Gemini Engine attacks, create a colorless Construct artifact creature token named Twin that's attacking. Its power is equal to Gemini Engine's power and its toughness is equal to Gemini Engine's toughness. Sacrifice the token at end of combat.
        this.addAbility(new AttacksTriggeredAbility(new GeminiEngineCreateTokenEffect(), false));
    }

    private GeminiEngine(final GeminiEngine card) {
        super(card);
    }

    @Override
    public GeminiEngine copy() {
        return new GeminiEngine(this);
    }
}

class GeminiEngineCreateTokenEffect extends OneShotEffect {

    GeminiEngineCreateTokenEffect() {
        super(Outcome.Benefit);
        this.staticText = "create a colorless Construct artifact creature token named Twin that's attacking. Its power is equal to {this}'s power and its toughness is equal to {this}'s toughness. Sacrifice the token at end of combat.";
    }

    private GeminiEngineCreateTokenEffect(final GeminiEngineCreateTokenEffect effect) {
        super(effect);
    }

    @Override
    public GeminiEngineCreateTokenEffect copy() {
        return new GeminiEngineCreateTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        Token token;
        if (permanent != null) {
            token = new GeminiEngineTwinToken(permanent.getPower().getValue(), permanent.getToughness().getValue());
        } else {
            token = new GeminiEngineTwinToken(0, 0);
        }
        token.putOntoBattlefield(1, game, source, source.getControllerId(), false, true);
        for (UUID tokenId : token.getLastAddedTokenIds()) {
            Permanent tokenPerm = game.getPermanent(tokenId);
            if (tokenPerm != null) {
                Effect effect = new SacrificeTargetEffect("sacrifice " + tokenPerm.getLogName(), player.getId());
                effect.setTargetPointer(new FixedTarget(tokenPerm, game));
                game.addDelayedTriggeredAbility(new AtTheEndOfCombatDelayedTriggeredAbility(effect), source);
            }
        }
        return true;
    }
}

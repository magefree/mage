package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.ElementalTokenWithHaste;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class FeralLightning extends CardImpl {

    public FeralLightning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{R}{R}");

        // Create three 3/1 red Elemental creature tokens with haste. Exile them at the beginning of the next end step.
        this.getSpellAbility().addEffect(new FeralLightningEffect());

    }

    private FeralLightning(final FeralLightning card) {
        super(card);
    }

    @Override
    public FeralLightning copy() {
        return new FeralLightning(this);
    }
}

class FeralLightningEffect extends OneShotEffect {

    public FeralLightningEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Create three 3/1 red Elemental creature tokens with haste. Exile them at the beginning of the next end step";
    }

    private FeralLightningEffect(final FeralLightningEffect effect) {
        super(effect);
    }

    @Override
    public FeralLightningEffect copy() {
        return new FeralLightningEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            CreateTokenEffect effect = new CreateTokenEffect(new ElementalTokenWithHaste(), 3);
            effect.apply(game, source);
            effect.exileTokensCreatedAtNextEndStep(game, source);
            return true;
        }

        return false;
    }
}
package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.game.permanent.token.custom.CreatureToken;
import mage.players.Player;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MercurialTransformation extends CardImpl {

    public MercurialTransformation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}");

        this.subtype.add(SubType.LESSON);

        // Until end of turn, target nonland permanent loses all abilities and becomes your choice of a blue Frog creature with base power and toughness 1/1 or a blue Octopus creature with base power and toughness 4/4.
        this.getSpellAbility().addEffect(new MercurialTransformationEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    private MercurialTransformation(final MercurialTransformation card) {
        super(card);
    }

    @Override
    public MercurialTransformation copy() {
        return new MercurialTransformation(this);
    }
}

class MercurialTransformationEffect extends OneShotEffect {

    MercurialTransformationEffect() {
        super(Outcome.Benefit);
        staticText = "until end of turn, target nonland permanent loses all abilities and " +
                "becomes your choice of a blue Frog creature with base power and toughness 1/1 " +
                "or a blue Octopus creature with base power and toughness 4/4";
    }

    private MercurialTransformationEffect(final MercurialTransformationEffect effect) {
        super(effect);
    }

    @Override
    public MercurialTransformationEffect copy() {
        return new MercurialTransformationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Token token;
        if (player.chooseUse(
                outcome, "1/1 Frog or 4/4 Octopus?",
                null, "Frog", "Octopus", source, game
        )) {
            token = new CreatureToken(1, 1).withColor("U").withSubType(SubType.FROG);
        } else {
            token = new CreatureToken(4, 4).withColor("U").withSubType(SubType.OCTOPUS);
        }
        game.addEffect(new BecomesCreatureTargetEffect(
                token, true, false, Duration.EndOfTurn
        ).withDurationRuleAtStart(true), source);
        return true;
    }
}

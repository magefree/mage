package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.token.BallisticBoulderToken;
import mage.game.permanent.token.Token;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FireNavyTrebuchet extends CardImpl {

    public FireNavyTrebuchet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever you attack, create a 2/1 colorless Construct artifact creature token with flying named Ballistic Boulder that's tapped and attacking. Sacrifice that token at the beginning of the next end step.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(new FireNavyTrebuchetEffect(), 1));
    }

    private FireNavyTrebuchet(final FireNavyTrebuchet card) {
        super(card);
    }

    @Override
    public FireNavyTrebuchet copy() {
        return new FireNavyTrebuchet(this);
    }
}

class FireNavyTrebuchetEffect extends OneShotEffect {

    FireNavyTrebuchetEffect() {
        super(Outcome.Benefit);
        staticText = "create a 2/1 colorless Construct artifact creature token with flying named Ballistic Boulder " +
                "that's tapped and attacking. Sacrifice that token at the beginning of the next end step.";
    }

    private FireNavyTrebuchetEffect(final FireNavyTrebuchetEffect effect) {
        super(effect);
    }

    @Override
    public FireNavyTrebuchetEffect copy() {
        return new FireNavyTrebuchetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new BallisticBoulderToken();
        token.putOntoBattlefield(1, game, source, source.getControllerId(), true, true);
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new SacrificeTargetEffect()
                        .setTargetPointer(new FixedTargets(token, game))
                        .setText("sacrifice those tokens")
        ), source);
        return true;
    }
}

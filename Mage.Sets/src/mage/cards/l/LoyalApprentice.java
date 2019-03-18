package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.condition.common.CommanderInPlayCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.constants.SubType;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.token.ThopterColorlessToken;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class LoyalApprentice extends CardImpl {

    public LoyalApprentice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Lieutenant — At the beginning of combat on your turn, if you control your commander, create a 1/1 colorless Thopter artifact creature token with flying. That token gains haste until end of turn.
        this.addAbility(new ConditionalTriggeredAbility(
                new BeginningOfCombatTriggeredAbility(
                        new LoyalApprenticeEffect(),
                        TargetController.YOU, false
                ), CommanderInPlayCondition.instance,
                "<i>Lieutenant</i> &mdash; At the beginning of combat "
                + "on your turn, create a 1/1 colorless Thopter "
                + "artifact creature token with flying. "
                + "That token gains haste until end of turn"
        ));
    }

    public LoyalApprentice(final LoyalApprentice card) {
        super(card);
    }

    @Override
    public LoyalApprentice copy() {
        return new LoyalApprentice(this);
    }
}

class LoyalApprenticeEffect extends OneShotEffect {

    public LoyalApprenticeEffect() {
        super(Outcome.Benefit);
    }

    public LoyalApprenticeEffect(final LoyalApprenticeEffect effect) {
        super(effect);
    }

    @Override
    public LoyalApprenticeEffect copy() {
        return new LoyalApprenticeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CreateTokenEffect effect = new CreateTokenEffect(new ThopterColorlessToken());
        effect.apply(game, source);
        effect.getLastAddedTokenIds().stream().map((tokenId) -> {
            ContinuousEffect continuousEffect = new GainAbilityTargetEffect(
                    HasteAbility.getInstance(), Duration.EndOfTurn
            );
            continuousEffect.setTargetPointer(new FixedTarget(tokenId, game));
            return continuousEffect;
        }).forEachOrdered((continuousEffect) -> {
            game.addEffect(continuousEffect, source);
        });
        return true;
    }
}

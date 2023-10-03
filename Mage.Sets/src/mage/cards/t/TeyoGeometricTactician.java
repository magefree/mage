package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.continuous.PlayerCanOnlyAttackInDirectionRestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.WallFlyingToken;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TeyoGeometricTactician extends CardImpl {

    public TeyoGeometricTactician(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TEYO);
        this.setStartingLoyalty(3);

        // When Teyo, Geometric Tactician enters the battlefield, create a 0/4 white Wall creature token with defender and flying.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new CreateTokenEffect(new WallFlyingToken())
        ));

        // +1: You and target opponent each draw a card.
        Ability ability = new LoyaltyAbility(
                new DrawCardSourceControllerEffect(1).setText("you"),
                1
        );
        ability.addEffect(
                new DrawCardTargetEffect(1)
                        .setText("and target opponent each draw a card")
        );
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // -2: Choose left or right. Until your next turn, each player may attack only the nearest opponent in the last chosen direction and planeswalkers controlled by that opponent.
        ability = new LoyaltyAbility(
                PlayerCanOnlyAttackInDirectionRestrictionEffect.choiceEffect(),
                -2
        );
        ability.addEffect(new PlayerCanOnlyAttackInDirectionRestrictionEffect(
                Duration.UntilYourNextTurn,
                "the last chosen direction"
        ));
        this.addAbility(ability);
    }

    private TeyoGeometricTactician(final TeyoGeometricTactician card) {
        super(card);
    }

    @Override
    public TeyoGeometricTactician copy() {
        return new TeyoGeometricTactician(this);
    }
}

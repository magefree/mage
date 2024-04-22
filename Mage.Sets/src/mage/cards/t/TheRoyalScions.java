package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheRoyalScions extends CardImpl {

    public TheRoyalScions(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.WILL);
        this.subtype.add(SubType.ROWAN);
        this.setStartingLoyalty(5);

        // +1: Draw a card, then discard a card.
        this.addAbility(new LoyaltyAbility(new DrawDiscardControllerEffect(1, 1), 1));

        // +1: Target creature gets +2/+0 and gains first strike and trample until end of turn.
        Ability ability = new LoyaltyAbility(new BoostTargetEffect(
                2, 0, Duration.EndOfTurn
        ).setText("Target creature gets +2/+0"), 1);
        ability.addEffect(new GainAbilityTargetEffect(
                FirstStrikeAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains first strike"));
        ability.addEffect(new GainAbilityTargetEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn
        ).setText("and trample until end of turn"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // −8: Draw four cards. When you do, The Royal Scions deals damage to any target equal to the number of cards in your hand.
        this.addAbility(new LoyaltyAbility(new TheRoyalScionsCreateReflexiveTriggerEffect(), -8));
    }

    private TheRoyalScions(final TheRoyalScions card) {
        super(card);
    }

    @Override
    public TheRoyalScions copy() {
        return new TheRoyalScions(this);
    }
}

class TheRoyalScionsCreateReflexiveTriggerEffect extends OneShotEffect {

    private static final Effect effect = new DrawCardSourceControllerEffect(4);

    TheRoyalScionsCreateReflexiveTriggerEffect() {
        super(Outcome.Benefit);
        staticText = "Draw four cards. When you do, {this} deals damage " +
                "to any target equal to the number of cards in your hand.";
    }

    private TheRoyalScionsCreateReflexiveTriggerEffect(final TheRoyalScionsCreateReflexiveTriggerEffect effect) {
        super(effect);
    }

    @Override
    public TheRoyalScionsCreateReflexiveTriggerEffect copy() {
        return new TheRoyalScionsCreateReflexiveTriggerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        effect.apply(game, source);
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new DamageTargetEffect(CardsInControllerHandCount.instance), false,
                "{this} deals damage to any target equal to the number of cards in your hand"
        );
        ability.addTarget(new TargetAnyTarget());
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}

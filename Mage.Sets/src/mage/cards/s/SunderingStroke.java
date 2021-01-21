package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.StaticHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.game.Game;
import mage.target.common.TargetAnyTargetAmount;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SunderingStroke extends CardImpl {

    public SunderingStroke(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{R}");

        // Sundering Stroke deals 7 damage divided as you choose among one, two, or three targets. If at least seven red mana was spent to cast this spell, instead Sundering Stroke deals 7 damage to each of those permanents and/or players.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageTargetEffect(7), new DamageMultiEffect(7), SunderingStrokeCondtition.instance,
                "{this} deals 7 damage divided as you choose among one, two, or three targets. " +
                        "If at least seven red mana was spent to cast this spell, " +
                        "instead {this} deals 7 damage to each of those permanents and/or players"
        ));
        this.getSpellAbility().addTarget(new TargetAnyTargetAmount(7, 3));
        this.getSpellAbility().addHint(new StaticHint(
                "(You have to choose how 7 damage is divided even if you spend seven red mana.)"
        ));
    }

    private SunderingStroke(final SunderingStroke card) {
        super(card);
    }

    @Override
    public SunderingStroke copy() {
        return new SunderingStroke(this);
    }
}

enum SunderingStrokeCondtition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return source.getManaCostsToPay().getUsedManaToPay().getColor(ColoredManaSymbol.R) >= 6;
    }
}
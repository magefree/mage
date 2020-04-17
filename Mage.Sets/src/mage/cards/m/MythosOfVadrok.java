package mage.cards.m;

import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.abilities.effects.common.DetainTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.target.common.TargetCreatureOrPlaneswalkerAmount;
import mage.watchers.common.ManaSpentToCastWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MythosOfVadrok extends CardImpl {

    private static final Condition condition = new CompoundCondition(
            new ManaWasSpentCondition(ColoredManaSymbol.W),
            new ManaWasSpentCondition(ColoredManaSymbol.U)
    );

    public MythosOfVadrok(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}{R}");

        // Mythos of Vadrok deals 5 damage divided as you choose among any number of target creatures and/or planeswalkers. If {W}{U} was spent to cast this spell, until your next turn, those permanents can't attack or block and their activated abilities can't be activated.
        this.getSpellAbility().addEffect(new DamageMultiEffect(5));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalkerAmount(5));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DetainTargetEffect(), condition, "If {W}{U} was spent to cast this spell, " +
                "until your next turn, those permanents can't attack or block " +
                "and their activated abilities can't be activated."
        ));
        this.getSpellAbility().addWatcher(new ManaSpentToCastWatcher());
    }

    private MythosOfVadrok(final MythosOfVadrok card) {
        super(card);
    }

    @Override
    public MythosOfVadrok copy() {
        return new MythosOfVadrok(this);
    }
}

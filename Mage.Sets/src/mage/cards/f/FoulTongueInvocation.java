package mage.cards.f;

import mage.abilities.condition.common.RevealedOrControlledDragonCondition;
import mage.abilities.costs.common.RevealDragonFromHandCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;
import mage.watchers.common.DragonOnTheBattlefieldWhileSpellWasCastWatcher;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class FoulTongueInvocation extends CardImpl {

    public FoulTongueInvocation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // As an additional cost to cast Foul-Tongue Invocation, you may reveal a Dragon card from your hand.
        this.getSpellAbility().addCost(new RevealDragonFromHandCost());

        // Target player sacrifices a creature. If you revealed a Dragon card or controlled a Dragon as you cast Foul-Tongue Invocation, you gain 4 life.
        this.getSpellAbility().addEffect(new SacrificeEffect(
                StaticFilters.FILTER_PERMANENT_CREATURE, 1, "target player"
        ));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new GainLifeEffect(4), RevealedOrControlledDragonCondition.instance,
                "If you revealed a Dragon card or controlled a Dragon as you cast this spell, you gain 4 life."
        ));
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addWatcher(new DragonOnTheBattlefieldWhileSpellWasCastWatcher());
    }

    private FoulTongueInvocation(final FoulTongueInvocation card) {
        super(card);
    }

    @Override
    public FoulTongueInvocation copy() {
        return new FoulTongueInvocation(this);
    }
}

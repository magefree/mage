package mage.cards.s;

import mage.abilities.condition.common.RevealedOrControlledDragonCondition;
import mage.abilities.costs.common.RevealDragonFromHandCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;
import mage.watchers.common.DragonOnTheBattlefieldWhileSpellWasCastWatcher;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SilumgarsScorn extends CardImpl {

    public SilumgarsScorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{U}");

        // As an additional cost to cast Silumgar's Scorn, you may reveal a Dragon card from your hand.
        this.getSpellAbility().addCost(new RevealDragonFromHandCost());

        // Counter target spell unless its controller pays {1}. If you revealed a Dragon card or controlled a Dragon as you cast Silumgar's Scorn, counter that spell instead.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new CounterTargetEffect(), new CounterUnlessPaysEffect(new GenericManaCost(1)),
                RevealedOrControlledDragonCondition.instance, "counter target spell unless its controller pays {1}. " +
                "If you revealed a Dragon card or controlled a Dragon as you cast this spell, counter that spell instead"
        ));
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addWatcher(new DragonOnTheBattlefieldWhileSpellWasCastWatcher());
    }

    private SilumgarsScorn(final SilumgarsScorn card) {
        super(card);
    }

    @Override
    public SilumgarsScorn copy() {
        return new SilumgarsScorn(this);
    }
}

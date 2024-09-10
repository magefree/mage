package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.dynamicvalue.common.GreatestSharedCreatureTypeCount;
import mage.abilities.effects.common.PutIntoLibraryNFromTopTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SynchronizedEviction extends CardImpl {

    public SynchronizedEviction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{U}");

        // This spell costs {2} less to cast if you control two or more creatures that share a creature type.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(2, SynchronizedEvictionCondition.instance))
                .addHint(GreatestSharedCreatureTypeCount.getHint()).setRuleAtTheTop(true)
        );

        // Put target nonland permanent into its owner's library second from the top.
        this.getSpellAbility().addEffect(new PutIntoLibraryNFromTopTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    private SynchronizedEviction(final SynchronizedEviction card) {
        super(card);
    }

    @Override
    public SynchronizedEviction copy() {
        return new SynchronizedEviction(this);
    }
}

enum SynchronizedEvictionCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return GreatestSharedCreatureTypeCount.instance.calculate(game, source, null) >= 2;
    }

    @Override
    public String toString() {
        return "you control at least two creatures that share a creature type";
    }
}

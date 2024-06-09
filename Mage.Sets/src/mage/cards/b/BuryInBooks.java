package mage.cards.b;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceTargetsPermanentCondition;
import mage.abilities.effects.common.PutIntoLibraryNFromTopTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterAttackingCreature;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BuryInBooks extends CardImpl {

    private static final FilterPermanent filter = new FilterAttackingCreature("an attacking creature");
    private static final Condition condition = new SourceTargetsPermanentCondition(filter);

    public BuryInBooks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{U}");

        // This spell costs {2} less to cast if it targets an attacking creature.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(2, condition).setCanWorksOnStackOnly(true)
        ).setRuleAtTheTop(true));

        // Put target creature into its owner's library second from the top.
        this.getSpellAbility().addEffect(new PutIntoLibraryNFromTopTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private BuryInBooks(final BuryInBooks card) {
        super(card);
    }

    @Override
    public BuryInBooks copy() {
        return new BuryInBooks(this);
    }
}

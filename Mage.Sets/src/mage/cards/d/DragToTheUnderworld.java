package mage.cards.d;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DragToTheUnderworld extends CardImpl {

    public DragToTheUnderworld(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}{B}");

        // This spell costs {X} less to cast, where X is your devotion to black.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SpellCostReductionSourceEffect(DevotionCount.B))
                .addHint(DevotionCount.B.getHint())
                .setRuleAtTheTop(true));

        // Destroy target creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private DragToTheUnderworld(final DragToTheUnderworld card) {
        super(card);
    }

    @Override
    public DragToTheUnderworld copy() {
        return new DragToTheUnderworld(this);
    }
}
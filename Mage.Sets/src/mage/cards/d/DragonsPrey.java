package mage.cards.d;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceTargetsPermanentCondition;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.cost.SpellCostIncreaseSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DragonsPrey extends CardImpl {

    private static final Condition condition = new SourceTargetsPermanentCondition(
            new FilterPermanent(SubType.DRAGON, "a Dragon")
    );

    public DragonsPrey(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // This spell costs {2} more to cast if it targets a Dragon.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostIncreaseSourceEffect(2, condition).setCanWorksOnStackOnly(true)
        ).setRuleAtTheTop(true));

        // Destroy target creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private DragonsPrey(final DragonsPrey card) {
        super(card);
    }

    @Override
    public DragonsPrey copy() {
        return new DragonsPrey(this);
    }
}

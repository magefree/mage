package mage.cards.m;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.NightCondition;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.common.NightHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MoonragersSlash extends CardImpl {

    public MoonragersSlash(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // This spell costs {2} less to cast if it's night.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(2, NightCondition.instance)
        ).addHint(NightHint.instance).setRuleAtTheTop(true));

        // Moonrager's Slash deals 3 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private MoonragersSlash(final MoonragersSlash card) {
        super(card);
    }

    @Override
    public MoonragersSlash copy() {
        return new MoonragersSlash(this);
    }
}

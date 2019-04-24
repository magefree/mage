
package mage.cards.b;

import java.util.UUID;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.ImproviseAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class BattleAtTheBridge extends CardImpl {

    public BattleAtTheBridge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{B}");

        // Improvise  (Your artifacts can help cast this spell. Each artifact you tap after you're done activating mana abilities pays for {1}.)
        addAbility(new ImproviseAbility());

        // Target creature gets -X/-X until end of turn. You gain X life.
        DynamicValue x = new SignInversionDynamicValue(new ManacostVariableValue());
        this.getSpellAbility().addEffect(new BoostTargetEffect(x, x, Duration.EndOfTurn, true));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new GainLifeEffect(new ManacostVariableValue()));
    }

    public BattleAtTheBridge(final BattleAtTheBridge card) {
        super(card);
    }

    @Override
    public BattleAtTheBridge copy() {
        return new BattleAtTheBridge(this);
    }
}

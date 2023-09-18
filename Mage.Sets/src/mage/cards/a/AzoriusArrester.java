
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DetainTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class AzoriusArrester extends CardImpl {
    
    public AzoriusArrester(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Azorius Arrester enters the battlefield, detain target creature an opponent controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DetainTargetEffect(), false);
        TargetCreaturePermanent target = new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE);
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private AzoriusArrester(final AzoriusArrester card) {
        super(card);
    }

    @Override
    public AzoriusArrester copy() {
        return new AzoriusArrester(this);
    }
}


package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class HanSolo extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Starship you control");

    static {
        filter.add(SubType.STARSHIP.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public HanSolo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // At the beginning of each combat, target starship you control gets +2/+2 and gains haste until end of turn.
        Effect effect = new BoostTargetEffect(2, 2, Duration.EndOfTurn);
        effect.setText("target Starship you control gets +2/+2");
        BeginningOfCombatTriggeredAbility ability = new BeginningOfCombatTriggeredAbility(Zone.BATTLEFIELD, effect, TargetController.ANY, false, false);
        effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains haste until end of turn");
        ability.addEffect(effect);
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private HanSolo(final HanSolo card) {
        super(card);
    }

    @Override
    public HanSolo copy() {
        return new HanSolo(this);
    }
}

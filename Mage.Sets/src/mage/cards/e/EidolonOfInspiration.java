package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EidolonOfInspiration extends CardImpl {

    public EidolonOfInspiration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{W}{W}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of combat on your turn, target creature you control gets +2/+0 until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new BoostTargetEffect(2, 0), TargetController.YOU, false
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private EidolonOfInspiration(final EidolonOfInspiration card) {
        super(card);
    }

    @Override
    public EidolonOfInspiration copy() {
        return new EidolonOfInspiration(this);
    }
}

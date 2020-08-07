package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.CycleControllerTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SavaiThundermane extends CardImpl {

    public SavaiThundermane(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever you cycle a card, you may pay {2}. When you do, Savai Thundermane deals 2 damage to target creature and you gain 2 life.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new DamageTargetEffect(2), false,
                "{this} deals 2 damage to target creature and you gain 2 life"
        );
        ability.addEffect(new GainLifeEffect(2));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(new CycleControllerTriggeredAbility(
                new DoWhenCostPaid(ability, new GenericManaCost(2), "Pay {2}?")
        ));
    }

    private SavaiThundermane(final SavaiThundermane card) {
        super(card);
    }

    @Override
    public SavaiThundermane copy() {
        return new SavaiThundermane(this);
    }
}

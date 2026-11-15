package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.condition.common.FormidableCondition;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAttackingCreature;

/**
 *
 * @author muz
 */
public final class UnaChinRileyNumberOne extends CardImpl {

    public UnaChinRileyNumberOne(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ILLYRIAN);
        this.subtype.add(SubType.OFFICER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Whenever you attack while creatures you control have total power 8 or greater, target attacking creature gets +2/+0 and gains first strike until end of turn.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(new BoostTargetEffect(2, 0), 1)
            .withTriggerCondition(FormidableCondition.instance);
        ability.addEffect(new AddContinuousEffectToGame(
            new GainAbilityTargetEffect(FirstStrikeAbility.getInstance())
        ).setText("and gains first strike until end of turn"));
        ability.addTarget(new TargetAttackingCreature());
        this.addAbility(ability);
    }

    private UnaChinRileyNumberOne(final UnaChinRileyNumberOne card) {
        super(card);
    }

    @Override
    public UnaChinRileyNumberOne copy() {
        return new UnaChinRileyNumberOne(this);
    }
}

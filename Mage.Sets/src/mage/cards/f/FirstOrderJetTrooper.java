package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author Merlingilb
 */
public class FirstOrderJetTrooper extends CardImpl {
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();
    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(SubType.TROOPER.getPredicate());
    }
    public FirstOrderJetTrooper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}");
        this.addSubType(SubType.TROOPER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        //Trooper creatures you control have haste and first strike.
        Ability ability = new SimpleStaticAbility(new GainAbilityControlledEffect(HasteAbility.getInstance(),
                Duration.WhileOnBattlefield, filter).setText("Trooper creatures you control have haste and first strike."));
        ability.addEffect(new GainAbilityControlledEffect(FirstStrikeAbility.getInstance(),
                Duration.WhileOnBattlefield, filter).setText(""));
        this.addAbility(ability);
    }

    public FirstOrderJetTrooper(FirstOrderJetTrooper card) {
        super(card);
    }

    @Override
    public FirstOrderJetTrooper copy() {
        return new FirstOrderJetTrooper(this);
    }
}

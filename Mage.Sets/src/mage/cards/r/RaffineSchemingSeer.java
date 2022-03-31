package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.AttackingCreatureCount;
import mage.abilities.effects.keyword.ConniveTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RaffineSchemingSeer extends CardImpl {

    private static final DynamicValue xValue = new AttackingCreatureCount("attacking creatures");
    private static final Hint hint = new ValueHint("Attacking creatures", xValue);

    public RaffineSchemingSeer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPHINX);
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Ward {1}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{1}")));

        // Whenever you attack, target creature connives X, where X is the number of attacking creatures.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(new ConniveTargetEffect(xValue), 1);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private RaffineSchemingSeer(final RaffineSchemingSeer card) {
        super(card);
    }

    @Override
    public RaffineSchemingSeer copy() {
        return new RaffineSchemingSeer(this);
    }
}

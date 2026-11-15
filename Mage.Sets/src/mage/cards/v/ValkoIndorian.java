package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.RulebreakerAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class ValkoIndorian extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Phyrexian creatures");

    static {
        filter.add(SubType.PHYREXIAN.getPredicate());
    }

    public ValkoIndorian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Rulebreaker -- A deck with this commander can have Phyrexian cards of any color identity and any basic land cards.
        this.addAbility(RulebreakerAbility.subtypeRuleBreaker(SubType.PHYREXIAN));

        // Phyrexian creatures you control have menace and lifelink.
        Ability ability = new SimpleStaticAbility(new GainAbilityControlledEffect(
            new MenaceAbility(false), Duration.WhileOnBattlefield, filter, false
        ));
        ability.addEffect(new GainAbilityControlledEffect(
            LifelinkAbility.getInstance(), Duration.WhileOnBattlefield, filter, false
        ).setText("and lifelink"));
        this.addAbility(ability);
    }

    private ValkoIndorian(final ValkoIndorian card) {
        super(card);
    }

    @Override
    public ValkoIndorian copy() {
        return new ValkoIndorian(this);
    }
}

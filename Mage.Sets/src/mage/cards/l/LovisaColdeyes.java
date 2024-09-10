
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

/**
 *
 * @author LoneFox
 */
public final class LovisaColdeyes extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature that's a Barbarian, a Warrior, or a Berserker");

    static {
        filter.add(Predicates.or(SubType.BARBARIAN.getPredicate(), SubType.WARRIOR.getPredicate(), SubType.BERSERKER.getPredicate()));
    }

    public LovisaColdeyes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Each creature that's a Barbarian, a Warrior, or a Berserker gets +2/+2 and has haste.
        Effect effect = new BoostAllEffect(2, 2, Duration.WhileOnBattlefield, filter, false);
        effect.setText("Each creature that's a Barbarian, a Warrior, or a Berserker gets +2/+2");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        effect = new GainAbilityAllEffect(HasteAbility.getInstance(), Duration.WhileOnBattlefield, filter, false);
        effect.setText("and has haste");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private LovisaColdeyes(final LovisaColdeyes card) {
        super(card);
    }

    @Override
    public LovisaColdeyes copy() {
        return new LovisaColdeyes(this);
    }
}

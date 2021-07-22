
package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.BattalionAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.VariableManaCostPredicate;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class FrontlineMedic extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("spell with {X} in its mana cost");

    static {
        filter.add(VariableManaCostPredicate.instance);
    }

    public FrontlineMedic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.color.setWhite(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Battalion - Whenever Frontline Medic and at least two other creatures attack, creatures you control gain indestructible until end of turn.
        Effect effect = new GainAbilityAllEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn, new FilterControlledCreaturePermanent(), false);
        effect.setText("creatures you control gain indestructible until end of turn");
        this.addAbility(new BattalionAbility(effect));

        // Sacrifice Frontline Medic: Counter target spell with {X} in its mana cost unless its controller pays {3}.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CounterUnlessPaysEffect(new GenericManaCost(3)), new SacrificeSourceCost());
        ability.addTarget(new TargetSpell(filter));
        this.addAbility(ability);

    }

    private FrontlineMedic(final FrontlineMedic card) {
        super(card);
    }

    @Override
    public FrontlineMedic copy() {
        return new FrontlineMedic(this);
    }
}

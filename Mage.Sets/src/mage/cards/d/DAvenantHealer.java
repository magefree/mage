package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetAttackingOrBlockingCreature;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class DAvenantHealer extends CardImpl {

    public DAvenantHealer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {T}: D'Avenant Healer deals 1 damage to target attacking or blocking creature.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(1), new TapSourceCost());
        ability.addTarget(new TargetAttackingOrBlockingCreature());
        this.addAbility(ability);

        // {T}: Prevent the next 1 damage that would be dealt to any target this turn.
        ability = new SimpleActivatedAbility(
                new PreventDamageToTargetEffect(Duration.EndOfTurn, 1), new TapSourceCost()
        );
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private DAvenantHealer(final DAvenantHealer card) {
        super(card);
    }

    @Override
    public DAvenantHealer copy() {
        return new DAvenantHealer(this);
    }
}

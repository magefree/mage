package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LightningCoreExcavator extends CardImpl {

    public LightningCoreExcavator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // {5}, {T}, Sacrifice Lightning-Core Excavator: It deals 3 damage to any target.
        Ability ability = new SimpleActivatedAbility(
                new DamageTargetEffect(3), new GenericManaCost(5)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private LightningCoreExcavator(final LightningCoreExcavator card) {
        super(card);
    }

    @Override
    public LightningCoreExcavator copy() {
        return new LightningCoreExcavator(this);
    }
}

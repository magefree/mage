package mage.cards.c;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 * @author TheElk801
 */
public final class CacophonyScamp extends CardImpl {

    private static final DynamicValue xValue = new SourcePermanentPowerCount();

    public CacophonyScamp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Cacophony Scamp deals combat damage to a player, you may sacrifice it. If you do, proliferate.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DoIfCostPaid(
                new ProliferateEffect(), new SacrificeSourceCost().setText("sacrifice it")
        ), false));

        // When Cacophony Scamp dies, it deals damage equal to its power to any target.
        Ability ability = new DiesSourceTriggeredAbility(new DamageTargetEffect(xValue, "it"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private CacophonyScamp(final CacophonyScamp card) {
        super(card);
    }

    @Override
    public CacophonyScamp copy() {
        return new CacophonyScamp(this);
    }
}

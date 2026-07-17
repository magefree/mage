package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ShrinesYouControlCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author Loki
 */
public final class HondenOfInfiniteRage extends CardImpl {

    public HondenOfInfiniteRage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHRINE);

        // At the beginning of your upkeep, Honden of Infinite Rage deals damage to any target equal to the number of Shrines you control.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                new DamageTargetEffect(ShrinesYouControlCount.WHERE_X)
                        .setText("{this} deals damage to any target equal to the number of Shrines you control")
        ).addHint(ShrinesYouControlCount.getHint());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private HondenOfInfiniteRage(final HondenOfInfiniteRage card) {
        super(card);
    }

    @Override
    public HondenOfInfiniteRage copy() {
        return new HondenOfInfiniteRage(this);
    }

}

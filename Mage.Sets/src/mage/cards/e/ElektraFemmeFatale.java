package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.DealDamageToControllerSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetCreaturePermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class ElektraFemmeFatale extends CardImpl {

    public ElektraFemmeFatale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Elektra enters, you may have her deal 2 damage to you. When you do, she deals 4 damage to target creature.
        ReflexiveTriggeredAbility reflexiveAbility = new ReflexiveTriggeredAbility(
            new DamageTargetEffect(4, "she"),
            false
        );
        reflexiveAbility.addTarget(new TargetCreaturePermanent());
        this.addAbility(new EntersBattlefieldTriggeredAbility(
            new DoWhenCostPaid(
                reflexiveAbility,
                new DealDamageToControllerSourceCost(2),
                "Have {this} deal 2 damage to you?"
            )
        ));
    }

    private ElektraFemmeFatale(final ElektraFemmeFatale card) {
        super(card);
    }

    @Override
    public ElektraFemmeFatale copy() {
        return new ElektraFemmeFatale(this);
    }
}

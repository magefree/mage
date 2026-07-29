package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.ForEachPlayerTargetsAdjuster;
import mage.target.targetpointer.EachTargetPointer;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author muz
 */
public final class TriSentinelActOfVengeance extends CardImpl {

    public TriSentinelActOfVengeance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{7}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Menace
        this.addAbility(new MenaceAbility());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Tri-Sentinel enters, for each opponent, Tri-Sentinel deals 3 damage to up to one target creature that player controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(3)
            .setTargetPointer(new EachTargetPointer())
            .setText("for each opponent, {this} deals 3 damage to up to one target creature that player controls")
        );
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        ability.setTargetAdjuster(new ForEachPlayerTargetsAdjuster(false, true));
        this.addAbility(ability);

        // Unearth {7}
        this.addAbility(new UnearthAbility(new ManaCostsImpl<>("{7}")));
    }

    private TriSentinelActOfVengeance(final TriSentinelActOfVengeance card) {
        super(card);
    }

    @Override
    public TriSentinelActOfVengeance copy() {
        return new TriSentinelActOfVengeance(this);
    }
}

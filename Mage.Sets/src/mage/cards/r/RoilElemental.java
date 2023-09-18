package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class RoilElemental extends CardImpl {

    public RoilElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}{U}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Landfall - Whenever a land enters the battlefield under your control, you may gain control of target creature for as long as you control Roil Elemental.
        Ability ability = new LandfallAbility(new GainControlTargetEffect(Duration.WhileControlled), true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private RoilElemental(final RoilElemental card) {
        super(card);
    }

    @Override
    public RoilElemental copy() {
        return new RoilElemental(this);
    }
}

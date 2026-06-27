package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
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
public final class GravitonFundamentalForce extends CardImpl {

    public GravitonFundamentalForce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you draw your second card each turn, choose one --
        // * Target creature gains flying until end of turn.
        Ability ability = new DrawNthCardTriggeredAbility(
            new GainAbilityTargetEffect(FlyingAbility.getInstance()),
            false, 2
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // * Tap target creature.
        ability.addMode(new Mode(
            new TapTargetEffect()
        ).addTarget(new TargetCreaturePermanent()));
    }

    private GravitonFundamentalForce(final GravitonFundamentalForce card) {
        super(card);
    }

    @Override
    public GravitonFundamentalForce copy() {
        return new GravitonFundamentalForce(this);
    }
}

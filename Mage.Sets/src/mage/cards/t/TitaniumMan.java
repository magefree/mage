package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetAnyTarget;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class TitaniumMan extends CardImpl {

    public TitaniumMan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever Titanium Man attacks, choose one --
        // * Titanium Man gains flying until end of turn.
        Ability ability = new AttacksTriggeredAbility(
            new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn)
        );

        // * Titanium Man deals 1 damage to any target.
        Mode mode = new Mode(new DamageTargetEffect(1));
        mode.addTarget(new TargetAnyTarget());
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private TitaniumMan(final TitaniumMan card) {
        super(card);
    }

    @Override
    public TitaniumMan copy() {
        return new TitaniumMan(this);
    }
}

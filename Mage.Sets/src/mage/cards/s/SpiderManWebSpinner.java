package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class SpiderManWebSpinner extends CardImpl {

    public SpiderManWebSpinner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIDER);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Spider-Man attacks, target creature can't block this turn.
        AttacksTriggeredAbility ability = new AttacksTriggeredAbility(new CantBlockTargetEffect(Duration.EndOfTurn), false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private SpiderManWebSpinner(final SpiderManWebSpinner card) {
        super(card);
    }

    @Override
    public SpiderManWebSpinner copy() {
        return new SpiderManWebSpinner(this);
    }
}

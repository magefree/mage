package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepSourceEffect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class DragonTurtle extends CardImpl {

    public DragonTurtle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");

        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Drag Below — When Dragon Turtle enters the battlefield, tap it and up to one target creature an opponent controls. They don't untap during their controllers' next untap steps.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapSourceEffect().setText("tap it"));
        ability.addEffect(new TapTargetEffect("and up to one target creature an opponent controls"));
        ability.addEffect(new DontUntapInControllersNextUntapStepSourceEffect().setText("They"));
        ability.addEffect(new DontUntapInControllersNextUntapStepTargetEffect().setText(" don't untap during their controllers' next untap steps"));
        ability.addTarget(new TargetOpponentsCreaturePermanent(0, 1));
        this.addAbility(ability.withFlavorWord("Drag Below"));
    }

    private DragonTurtle(final DragonTurtle card) {
        super(card);
    }

    @Override
    public DragonTurtle copy() {
        return new DragonTurtle(this);
    }
}

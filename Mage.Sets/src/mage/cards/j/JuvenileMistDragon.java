package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.EachOpponentPermanentTargetsAdjuster;
import mage.target.targetpointer.EachTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JuvenileMistDragon extends CardImpl {

    public JuvenileMistDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Confounding Clouds — When Juvenile Mist Dragon enters the battlefield, for each opponent, tap up to one target creature that player controls. Each of those creatures doesn't untap during its controller's next untap step.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new TapTargetEffect()
                        .setTargetPointer(new EachTargetPointer())
                        .setText("for each opponent, tap up to one target creature that player controls")
        );
        ability.addEffect(
                new DontUntapInControllersNextUntapStepTargetEffect("")
                        .setTargetPointer(new EachTargetPointer())
                        .setText("Each of those creatures doesn't untap during its controller's next untap step")
        );
        ability.addTarget(new TargetCreaturePermanent(0,1));
        ability.setTargetAdjuster(new EachOpponentPermanentTargetsAdjuster());
        this.addAbility(ability.withFlavorWord("Confounding Clouds"));
    }

    private JuvenileMistDragon(final JuvenileMistDragon card) {
        super(card);
    }

    @Override
    public JuvenileMistDragon copy() {
        return new JuvenileMistDragon(this);
    }
}

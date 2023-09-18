package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DreamshackleGeist extends CardImpl {

    public DreamshackleGeist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of combat on your turn, choose up to one —
        // • Tap target creature.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new TapTargetEffect(), TargetController.YOU, false
        );
        ability.addTarget(new TargetCreaturePermanent());
        ability.getModes().setMinModes(0);
        ability.getModes().setMaxModes(1);

        // • Target creature doesn't untap during its controller's next untap step.
        Mode mode = new Mode(new DontUntapInControllersNextUntapStepTargetEffect("target creature"));
        mode.addTarget(new TargetCreaturePermanent());
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private DreamshackleGeist(final DreamshackleGeist card) {
        super(card);
    }

    @Override
    public DreamshackleGeist copy() {
        return new DreamshackleGeist(this);
    }
}

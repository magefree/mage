package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactPermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SawbladeSlinger extends CardImpl {

    private static final FilterPermanent filter = new FilterArtifactPermanent("artifact an opponent controls");
    private static final FilterPermanent filter2 = new FilterPermanent(SubType.ZOMBIE, "Zombie and opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter2.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public SawbladeSlinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When Sawblade Slinger enters the battlefield, choose up to one —
        // • Destroy target artifact an opponent controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetPermanent(filter));
        ability.getModes().setMinModes(0);
        ability.getModes().setMaxModes(1);

        // • Sawblade Slinger fights target Zombie an opponent controls.
        Mode mode = new Mode(new FightTargetSourceEffect());
        mode.addTarget(new TargetPermanent(filter2));
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private SawbladeSlinger(final SawbladeSlinger card) {
        super(card);
    }

    @Override
    public SawbladeSlinger copy() {
        return new SawbladeSlinger(this);
    }
}

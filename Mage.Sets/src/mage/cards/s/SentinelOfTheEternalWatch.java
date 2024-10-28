package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIsActivePlayerPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SentinelOfTheEternalWatch extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature that player controls");

    static {
        filter.add(ControllerIsActivePlayerPredicate.instance);
    }

    public SentinelOfTheEternalWatch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}");
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // At the beginning of combat on each opponent's turn, tap target creature that player controls.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                TargetController.OPPONENT, new TapTargetEffect("tap target creature that player controls"),
                false
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private SentinelOfTheEternalWatch(final SentinelOfTheEternalWatch card) {
        super(card);
    }

    @Override
    public SentinelOfTheEternalWatch copy() {
        return new SentinelOfTheEternalWatch(this);
    }
}

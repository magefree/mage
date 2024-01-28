package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PrivateEye extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.DETECTIVE, "Detectives");
    private static final FilterPermanent filter2 = new FilterPermanent(SubType.DETECTIVE, "Detective");

    public PrivateEye(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");

        this.subtype.add(SubType.HOMUNCULUS);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Other Detectives you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter, true
        )));

        // Whenever you draw your second card each turn, target Detective can't be blocked this turn.
        Ability ability = new DrawNthCardTriggeredAbility(new CantBeBlockedTargetEffect(), false, 2);
        ability.addTarget(new TargetPermanent(filter2));
        this.addAbility(ability);
    }

    private PrivateEye(final PrivateEye card) {
        super(card);
    }

    @Override
    public PrivateEye copy() {
        return new PrivateEye(this);
    }
}

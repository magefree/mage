
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.FortifyAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class DarksteelGarrison extends CardImpl {

    public DarksteelGarrison(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.FORTIFICATION);

        // Fortified land is indestructible.
        Ability gainedAbility = IndestructibleAbility.getInstance();
        Effect effect = new GainAbilityAttachedEffect(gainedAbility, AttachmentType.AURA);
        effect.setText("Fortified land has indestructible");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

        // Whenever fortified land becomes tapped, target creature gets +1/+1 until end of turn.
        Ability ability = new BecomesTappedAttachedTriggeredAbility(new BoostTargetEffect(1, 1, Duration.EndOfTurn), "fortified land");
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Fortify {3}
        this.addAbility(new FortifyAbility(3));

    }

    private DarksteelGarrison(final DarksteelGarrison card) {
        super(card);
    }

    @Override
    public DarksteelGarrison copy() {
        return new DarksteelGarrison(this);
    }
}

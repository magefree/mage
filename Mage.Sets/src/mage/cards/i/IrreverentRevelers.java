package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetArtifactPermanent;

/**
 * @author TheElk801
 */
public final class IrreverentRevelers extends CardImpl {

    public IrreverentRevelers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.SATYR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Irreverent Revelers enters the battlefield, choose one —
        // • Destroy target artifact.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetArtifactPermanent());

        // • Irreverent Revelers gains haste until end of turn.
        ability.addMode(new Mode(new GainAbilitySourceEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        )));
        this.addAbility(ability);
    }

    private IrreverentRevelers(final IrreverentRevelers card) {
        super(card);
    }

    @Override
    public IrreverentRevelers copy() {
        return new IrreverentRevelers(this);
    }
}

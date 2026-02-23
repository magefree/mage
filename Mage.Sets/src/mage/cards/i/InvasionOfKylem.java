package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.ValorsReachTagTeamToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfKylem extends TransformingDoubleFacedCard {

    public InvasionOfKylem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.BATTLE}, new SubType[]{SubType.SIEGE}, "{2}{R}{W}",
                "Valor's Reach Tag Team",
                new CardType[]{CardType.SORCERY}, new SubType[]{}, "RW"
        );

        // Invasion of Kylem
        this.getLeftHalfCard().setStartingDefense(5);
        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.getLeftHalfCard().addAbility(new SiegeAbility());

        // When Invasion of Kylem enters the battlefield, up to two target creatures each get +2/+0 and gain vigilance and haste until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(2, 0)
                .setText("up to two target creatures each get +2/+0"));
        ability.addEffect(new GainAbilityTargetEffect(VigilanceAbility.getInstance())
                .setText("and gain vigilance"));
        ability.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance())
                .setText("and haste until end of turn"));
        ability.addTarget(new TargetCreaturePermanent(0, 2));
        this.getLeftHalfCard().addAbility(ability);

        // Valor's Reach Tag Team
        // Create two 3/2 red and white Warrior creature tokens with "Whenever this creature and at least one other creature token attack, put a +1/+1 counter on this creature."
        this.getRightHalfCard().getSpellAbility().addEffect(new CreateTokenEffect(new ValorsReachTagTeamToken(), 2));
    }

    private InvasionOfKylem(final InvasionOfKylem card) {
        super(card);
    }

    @Override
    public InvasionOfKylem copy() {
        return new InvasionOfKylem(this);
    }
}

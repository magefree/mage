package mage.cards.e;

import mage.abilities.effects.common.continuous.AddCardSubTypeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EnterTheAvatarState extends CardImpl {

    public EnterTheAvatarState(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        this.subtype.add(SubType.LESSON);

        // Until end of turn, target creature you control becomes an Avatar in addition to its other types and gains flying, first strike, lifelink, and hexproof.
        this.getSpellAbility().addEffect(new AddCardSubTypeTargetEffect(SubType.AVATAR, Duration.EndOfTurn)
                .setText("until end of turn, target creature you control becomes an Avatar in addition to its other types"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(FlyingAbility.getInstance()).setText("and gains flying"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(FirstStrikeAbility.getInstance()).setText(", first strike"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(LifelinkAbility.getInstance()).setText(", lifelink"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(HexproofAbility.getInstance()).setText(", and hexproof"));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private EnterTheAvatarState(final EnterTheAvatarState card) {
        super(card);
    }

    @Override
    public EnterTheAvatarState copy() {
        return new EnterTheAvatarState(this);
    }
}

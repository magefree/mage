package mage.cards.m;

import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class MalakirRebirth extends ModalDoubleFacedCard {

    public MalakirRebirth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.INSTANT}, new SubType[]{}, "{B}",
                "Malakir Mire", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Malakir Rebirth
        // Instant

        // Choose target creature. You lose 2 life. Until end of turn, that creature gains "When this creature dies, return it to the battlefield tapped under its owner's control."
        this.getLeftHalfCard().getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(2)
                .setText("Choose target creature. You lose 2 life"));
        this.getLeftHalfCard().getSpellAbility().addEffect(new GainAbilityTargetEffect(new DiesSourceTriggeredAbility(
                new ReturnSourceFromGraveyardToBattlefieldEffect(true, true), false
        ), Duration.EndOfTurn).setText("Until end of turn, that creature gains \"When this creature dies, return it to the battlefield tapped under its owner's control.\""));
        this.getLeftHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());

        // 2.
        // Malakir Mire
        // Land

        // Malakir Mire enters the battlefield tapped.
        this.getRightHalfCard().addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {B}.
        this.getRightHalfCard().addAbility(new BlackManaAbility());
    }

    private MalakirRebirth(final MalakirRebirth card) {
        super(card);
    }

    @Override
    public MalakirRebirth copy() {
        return new MalakirRebirth(this);
    }
}
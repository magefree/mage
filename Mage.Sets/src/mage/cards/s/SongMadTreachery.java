package mage.cards.s;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.mana.RedManaAbility;
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
public final class SongMadTreachery extends ModalDoubleFacedCard {

    public SongMadTreachery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.SORCERY}, new SubType[]{}, "{3}{R}{R}",
                "Song-Mad Ruins", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Song-Mad Treachery
        // Sorcery

        // Gain control of target creature until end of turn. Untap that creature. It gains haste until end of turn.
        this.getLeftHalfCard().getSpellAbility().addEffect(new GainControlTargetEffect(Duration.EndOfTurn));
        this.getLeftHalfCard().getSpellAbility().addEffect(new UntapTargetEffect().setText("Untap that creature"));
        this.getLeftHalfCard().getSpellAbility().addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText("It gains haste until end of turn."));
        this.getLeftHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());

        // 2.
        // Song-Mad Ruins
        // Land

        // Song-Mad Ruins enters the battlefield tapped.
        this.getRightHalfCard().addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {R}.
        this.getRightHalfCard().addAbility(new RedManaAbility());
    }

    private SongMadTreachery(final SongMadTreachery card) {
        super(card);
    }

    @Override
    public SongMadTreachery copy() {
        return new SongMadTreachery(this);
    }
}

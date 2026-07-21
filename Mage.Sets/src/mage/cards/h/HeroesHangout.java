package mage.cards.h;

import mage.abilities.Mode;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class HeroesHangout extends CardImpl {

    public HeroesHangout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");
        

        // Choose one --
        // * Date Night -- Exile the top two cards of your library. Choose one of them. Until the end of your next turn, you may play that card.
        this.getSpellAbility().addEffect(new ExileTopXMayPlayUntilEffect(2, true, Duration.UntilEndOfYourNextTurn));
        this.getSpellAbility().withFirstModeFlavorWord("Date Night");

        // * Patrol Night -- One or two target creatures each get +1/+0 and gain first strike until end of turn.
        Mode mode = new Mode(new BoostTargetEffect(1, 0));
        mode.addEffect(new GainAbilityTargetEffect(FirstStrikeAbility.getInstance()).setText("and gain first strike until end of turn"));
        mode.addTarget(new TargetCreaturePermanent(1, 2));
        mode.withFlavorWord("Patrol Night");

        this.getSpellAbility().addMode(mode);

    }

    private HeroesHangout(final HeroesHangout card) {
        super(card);
    }

    @Override
    public HeroesHangout copy() {
        return new HeroesHangout(this);
    }
}

package mage.cards.h;

import mage.abilities.Mode;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
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

        // * Patrol Night -- One or two target creatures each get +1/+0 and gain first strike until end of turn.
        this.getSpellAbility().addMode(new Mode(new BoostTargetEffect(1, 0)).addTarget(new TargetCreaturePermanent(1, 2)));

    }

    private HeroesHangout(final HeroesHangout card) {
        super(card);
    }

    @Override
    public HeroesHangout copy() {
        return new HeroesHangout(this);
    }
}

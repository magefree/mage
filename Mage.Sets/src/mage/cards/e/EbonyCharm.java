package mage.cards.e;

import mage.abilities.Mode;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInASingleGraveyard;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class EbonyCharm extends CardImpl {

    public EbonyCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Choose one - Target opponent loses 1 life and you gain 1 life;
        this.getSpellAbility().addEffect(new LoseLifeTargetEffect(1));
        this.getSpellAbility().addEffect(new GainLifeEffect(1).concatBy("and"));
        this.getSpellAbility().addTarget(new TargetOpponent());

        // or exile up to three target cards from a single graveyard; 
        Mode mode = new Mode(new ExileTargetEffect());
        mode.addTarget((new TargetCardInASingleGraveyard(0, 3, StaticFilters.FILTER_CARD_CARDS)));
        this.getSpellAbility().addMode(mode);

        // or target creature gains fear until end of turn.
        mode = new Mode(new GainAbilityTargetEffect(FearAbility.getInstance(), Duration.EndOfTurn));
        mode.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);
    }

    private EbonyCharm(final EbonyCharm card) {
        super(card);
    }

    @Override
    public EbonyCharm copy() {
        return new EbonyCharm(this);
    }
}

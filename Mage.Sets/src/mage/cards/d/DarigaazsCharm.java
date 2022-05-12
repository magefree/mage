package mage.cards.d;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author FenrisulfrX
 */
public final class DarigaazsCharm extends CardImpl {

    public DarigaazsCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}{R}{G}");

        // Choose one - Return target creature card from your graveyard to your hand;
        Effect effect = new ReturnToHandTargetEffect();
        effect.setText("Return target creature card from your graveyard to your hand");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE));

        // or Darigaaz's Charm deals 3 damage to any target;
        Mode mode = new Mode(new DamageTargetEffect(3));
        mode.addTarget(new TargetAnyTarget());
        this.getSpellAbility().addMode(mode);

        // or target creature gets +3/+3 until end of turn.
        mode = new Mode(new BoostTargetEffect(3, 3, Duration.EndOfTurn));
        mode.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);
    }

    private DarigaazsCharm(final DarigaazsCharm card) {
        super(card);
    }

    @Override
    public DarigaazsCharm copy() {
        return new DarigaazsCharm(this);
    }
}

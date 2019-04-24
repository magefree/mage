
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author jeffwadsworth
 */
public final class SoulManipulation extends CardImpl {

    public SoulManipulation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{B}");

        // Choose one or both -
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);

        // Counter target creature spell;
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_CREATURE));

        // and/or return target creature card from your graveyard to your hand.
        Mode mode = new Mode();
        mode.getEffects().add(new ReturnFromGraveyardToHandTargetEffect());
        mode.getTargets().add(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.getSpellAbility().addMode(mode);

    }

    public SoulManipulation(final SoulManipulation card) {
        super(card);
    }

    @Override
    public SoulManipulation copy() {
        return new SoulManipulation(this);
    }
}

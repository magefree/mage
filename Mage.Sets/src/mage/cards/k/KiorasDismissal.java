package mage.cards.k;

import mage.abilities.abilityword.StriveAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class KiorasDismissal extends CardImpl {

    public KiorasDismissal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");


        // Strive - Kiora's Dismissal costs U more to cast for each target beyond the first.
        this.addAbility(new StriveAbility("{U}"));

        // Return any number of target enchantments to their owners' hands.
        this.getSpellAbility().addTarget(new TargetPermanent(0, Integer.MAX_VALUE, StaticFilters.FILTER_PERMANENT_ENCHANTMENT, false));
        Effect effect = new ReturnToHandTargetEffect();
        effect.setText("Return any number of target enchantments to their owners' hands");
        this.getSpellAbility().addEffect(effect);
    }

    private KiorasDismissal(final KiorasDismissal card) {
        super(card);
    }

    @Override
    public KiorasDismissal copy() {
        return new KiorasDismissal(this);
    }
}

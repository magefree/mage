
package mage.cards.d;

import mage.abilities.condition.common.RevoltCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.watchers.common.RevoltWatcher;

import java.util.UUID;

/**
 *
 * @author emerald000
 */
public final class Decommission extends CardImpl {

    public Decommission(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Destroy target artifact or enchantment.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));

        // <i>Revolt</i> &mdash; If a permanent you controlled left the battlefield this turn, you gain 3 life.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new GainLifeEffect(3), RevoltCondition.instance, "<br><i>Revolt</i> &mdash; If a permanent you controlled left the battlefield this turn, you gain 3 life."));
        this.getSpellAbility().addWatcher(new RevoltWatcher());
    }

    private Decommission(final Decommission card) {
        super(card);
    }

    @Override
    public Decommission copy() {
        return new Decommission(this);
    }
}

package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

/**
 *
 * @author Plopman
 */
public final class ResoundingWave extends CardImpl {

    public ResoundingWave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}");


        // Return target permanent to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent());
        // Cycling {5}{W}{U}{B}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{5}{W}{U}{B}")));
        // When you cycle Resounding Wave, return two target permanents to their owners' hands.
        Ability ability = new CycleTriggeredAbility(new ReturnToHandTargetEffect());
        TargetPermanent target = new TargetPermanent(2, StaticFilters.FILTER_PERMANENTS);
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private ResoundingWave(final ResoundingWave card) {
        super(card);
    }

    @Override
    public ResoundingWave copy() {
        return new ResoundingWave(this);
    }
}

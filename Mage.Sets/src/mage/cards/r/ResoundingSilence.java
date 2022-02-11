package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetAttackingCreature;

/**
 *
 * @author Plopman
 */
public final class ResoundingSilence extends CardImpl {

    public ResoundingSilence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{W}");

        // Exile target attacking creature.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetAttackingCreature());
        // Cycling {5}{G}{W}{U}
        this.addAbility(new CyclingAbility(new ManaCostsImpl("{5}{G}{W}{U}")));
        // When you cycle Resounding Silence, exile up to two target attacking creatures.
        Ability ability = new CycleTriggeredAbility(new ExileTargetEffect());
        TargetPermanent target = new TargetAttackingCreature(0, 2, StaticFilters.FILTER_ATTACKING_CREATURES, false);
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private ResoundingSilence(final ResoundingSilence card) {
        super(card);
    }

    @Override
    public ResoundingSilence copy() {
        return new ResoundingSilence(this);
    }
}

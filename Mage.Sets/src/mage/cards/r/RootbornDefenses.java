package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PopulateEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author LevleX2
 */
public final class RootbornDefenses extends CardImpl {

    public RootbornDefenses(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}");


        // Populate. Creatures you control are indestructible this turn. 
        // (To populate, create a token that's a copy of a creature 
        // token you control. Damage and effects that say "destroy" don't destroy 
        // indestructible creatures.)
        this.getSpellAbility().addEffect(new PopulateEffect());
        Effect effect = new GainAbilityAllEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_CONTROLLED_CREATURES, false);
        this.getSpellAbility().addEffect(effect);

    }

    private RootbornDefenses(final RootbornDefenses card) {
        super(card);
    }

    @Override
    public RootbornDefenses copy() {
        return new RootbornDefenses(this);
    }
}

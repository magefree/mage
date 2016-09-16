package mage.sets.kaladesh;

import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.Filter;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.TargetSpell;
import mage.target.common.TargetNonBasicLandPermanent;

import java.util.UUID;

/**
 * Created by IGOUDT on 16-9-2016.
 */
public class RevolutionaryRebuff extends CardImpl {



    public RevolutionaryRebuff(final UUID ownerId){
        super(ownerId, 61, "Revolutionary Rebuff", Rarity.COMMON, new CardType[]{CardType.INSTANT},"{1U}");
        this.expansionSetCode = "KLD";

        FilterSpell filter = new FilterSpell();
        filter.add(Predicates.not(new SubtypePredicate("Artifact")));

        this.getSpellAbility().addTarget(new TargetSpell(filter) );
        
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(new GenericManaCost(2)));

    }

    public RevolutionaryRebuff(final RevolutionaryRebuff revolutionaryRebuff){
        super(revolutionaryRebuff);
    }

    @Override
    public RevolutionaryRebuff copy() {
        return new RevolutionaryRebuff(this);
    }
}

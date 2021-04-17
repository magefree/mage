package mage.abilities.keyword;

import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;

/**
 * @author LevelX2
 */
public class ProwessAbility extends SpellCastControllerTriggeredAbility {

    private static final FilterSpell filterNonCreatureSpell = new FilterSpell("noncreature spell");

    static {
        filterNonCreatureSpell.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public ProwessAbility() {
        super(new BoostSourceEffect(1, 1, Duration.EndOfTurn), false);
        this.filter = filterNonCreatureSpell;
    }

    public ProwessAbility(final ProwessAbility ability) {
        super(ability);
    }

    @Override
    public String getRule() {
        return "Prowess <i>(Whenever you cast a noncreature spell, this creature gets +1/+1 until end of turn.)</i>";
    }

    @Override
    public ProwessAbility copy() {
        return new ProwessAbility(this);
    }
}

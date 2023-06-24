package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.counter.AddPoisonCounterTargetEffect;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.game.command.Emblem;
import mage.target.common.TargetOpponent;

public final class AjaniSleeperAgentEmblem extends Emblem {

    private static final FilterSpell filter = new FilterSpell("a creature or planeswalker spell");

    static {
        filter.add(Predicates.or(CardType.CREATURE.getPredicate(), CardType.PLANESWALKER.getPredicate()));
    }

    // You get an emblem with "Whenever you cast a creature or planeswalker spell, target opponent gets two poison counters."
    public AjaniSleeperAgentEmblem() {
        super("Emblem Ajani");
        Ability ability = new SpellCastControllerTriggeredAbility(Zone.COMMAND, new AddPoisonCounterTargetEffect(2), filter, false, false);
        ability.addTarget(new TargetOpponent());
        this.getAbilities().add(ability);
    }

    private AjaniSleeperAgentEmblem(final AjaniSleeperAgentEmblem card) {
        super(card);
    }

    @Override
    public AjaniSleeperAgentEmblem copy() {
        return new AjaniSleeperAgentEmblem(this);
    }
}

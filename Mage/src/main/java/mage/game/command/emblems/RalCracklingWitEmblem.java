package mage.game.command.emblems;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.StormAbility;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.Predicates;
import mage.game.command.Emblem;

/**
 * @author PurpleCrowbar
 */
public final class RalCracklingWitEmblem extends Emblem {
    // Instant and sorcery spells you cast have storm.

    private static final FilterNonlandCard filter = new FilterNonlandCard("Instant and sorcery spells you cast");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()
        ));
    }

    public RalCracklingWitEmblem() {
        super("Emblem Ral");
        getAbilities().add(new SimpleStaticAbility(Zone.COMMAND, new GainAbilityControlledSpellsEffect(new StormAbility(), filter)));
    }

    private RalCracklingWitEmblem(final RalCracklingWitEmblem card) {
        super(card);
    }

    @Override
    public RalCracklingWitEmblem copy() {
        return new RalCracklingWitEmblem(this);
    }
}

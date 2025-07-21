package mage.cards.a;

import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.keyword.IncubateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AssimilateEssence extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("creature or battle spell");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.BATTLE.getPredicate()
        ));
    }

    public AssimilateEssence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Counter target creature or battle spell unless its controller pays {4}. If they do, you incubate 2.
        this.getSpellAbility().addEffect(
                new CounterUnlessPaysEffect(new GenericManaCost(4))
                        .withIfTheyDo(new IncubateEffect(2).setText("you incubate 2"))
        );
        this.getSpellAbility().addTarget(new TargetSpell(filter));
    }

    private AssimilateEssence(final AssimilateEssence card) {
        super(card);
    }

    @Override
    public AssimilateEssence copy() {
        return new AssimilateEssence(this);
    }
}
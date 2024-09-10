package mage.cards.g;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.AttackingCreatureCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.PreventAllDamageByAllPermanentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.ElfWarriorToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GaladhrimAmbush extends CardImpl {

    private static final DynamicValue xValue = new AttackingCreatureCount();
    private static final FilterPermanent filter = new FilterCreaturePermanent("non-Elf creatures");

    static {
        filter.add(Predicates.not(SubType.ELF.getPredicate()));
    }

    public GaladhrimAmbush(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{G}");

        // Create X 1/1 green Elf Warrior creature tokens, where X is the number of attacking creatures.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new ElfWarriorToken(), xValue)
                .setText("create X 1/1 green Elf Warrior creature tokens, where X is the number of attacking creatures"));

        // Prevent all combat damage that would be dealt this turn by non-Elf creatures.
        this.getSpellAbility().addEffect(new PreventAllDamageByAllPermanentsEffect(
                filter, Duration.EndOfTurn, true
        ).concatBy("<br>"));
    }

    private GaladhrimAmbush(final GaladhrimAmbush card) {
        super(card);
    }

    @Override
    public GaladhrimAmbush copy() {
        return new GaladhrimAmbush(this);
    }
}

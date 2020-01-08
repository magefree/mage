package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AltarOfThePantheon extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(Predicates.or(
                SubType.GOD.getPredicate(),
                SubType.DEMIGOD.getPredicate(),
                Predicates.and(
                        SuperType.LEGENDARY.getPredicate(),
                        CardType.ENCHANTMENT.getPredicate()
                )
        ));
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);

    public AltarOfThePantheon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Your devotion to each color and each combination of colors is increased by one.
        this.addAbility(new DevotionCount.IncreaseDevotionAbility());

        // {T}: Add one mana of any color. If you control a God, a Demigod, or a legendary enchantment, you gain 1 life.
        Ability ability = new AnyColorManaAbility();
        ability.addEffect(new ConditionalOneShotEffect(
                new GainLifeEffect(1), condition, "If you control " +
                "a God, a Demigod, or a legendary enchantment, you gain 1 life."
        ));
        this.addAbility(ability);
    }

    private AltarOfThePantheon(final AltarOfThePantheon card) {
        super(card);
    }

    @Override
    public AltarOfThePantheon copy() {
        return new AltarOfThePantheon(this);
    }
}

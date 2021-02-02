
package mage.cards.s;

import java.util.UUID;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.AddConditionalColorlessManaEffect;
import mage.abilities.mana.ActivateIfConditionManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.conditional.ConditionalSpellManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.mageobject.ColorlessPredicate;

/**
 * @author LevelX2
 */
public final class ShrineOfTheForsakenGods extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("colorless spells");

    static {
        filter.add(ColorlessPredicate.instance);
    }

    public ShrineOfTheForsakenGods(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}: Add {C}{C}. Spend this mana only to cast colorless spells. Activate this ability only if you control seven or more lands.
        this.addAbility(new ActivateIfConditionManaAbility(
                Zone.BATTLEFIELD,
                new AddConditionalColorlessManaEffect(2, new ConditionalSpellManaBuilder(filter)),
                new TapSourceCost(),
                new PermanentsOnTheBattlefieldCondition(new FilterControlledLandPermanent("you control seven or more lands"), ComparisonType.MORE_THAN, 6)));
    }

    private ShrineOfTheForsakenGods(final ShrineOfTheForsakenGods card) {
        super(card);
    }

    @Override
    public ShrineOfTheForsakenGods copy() {
        return new ShrineOfTheForsakenGods(this);
    }
}

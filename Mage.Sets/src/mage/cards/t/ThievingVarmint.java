package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.conditional.ConditionalSpellManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThievingVarmint extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("spells you don't own");

    static {
        filter.add(TargetController.NOT_YOU.getOwnerPredicate());
    }

    public ThievingVarmint(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.VARMINT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // {T}, Pay 1 life: Add two mana of any one color. Spend this mana only to cast spells you don't own.
        Ability ability = new ConditionalAnyColorManaAbility(
                new TapSourceCost(), 2,
                new ConditionalSpellManaBuilder(filter), true
        );
        ability.addCost(new PayLifeCost(1));
        this.addAbility(ability);
    }

    private ThievingVarmint(final ThievingVarmint card) {
        super(card);
    }

    @Override
    public ThievingVarmint copy() {
        return new ThievingVarmint(this);
    }
}

package mage.cards.s;

import java.util.UUID;
import mage.abilities.LoyaltyAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.mana.AddConditionalManaOfAnyColorEffect;
import mage.abilities.mana.conditional.ConditionalSpellManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterSpell;
import mage.game.permanent.token.DragonToken2;

/**
 *
 * @author TheElk801
 */
public final class SarkhanFireblood extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("Dragon spells");

    static {
        filter.add(SubType.DRAGON.getPredicate());
    }

    public SarkhanFireblood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{R}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SARKHAN);
        this.setStartingLoyalty(3);

        // +1: You may discard a card. If you do, draw a card.
        this.addAbility(new LoyaltyAbility(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(1),
                new DiscardCardCost()
        ), 1));

        // +1: Add two mana of any combination of colors. Spend this mana only to cast Dragon spells.
        this.addAbility(new LoyaltyAbility(
                new AddConditionalManaOfAnyColorEffect(
                        StaticValue.get(2),
                        StaticValue.get(2),
                        new ConditionalSpellManaBuilder(filter),
                        false
                ), 1
        ));

        // -7: Create four 5/5 red Dragon creature tokens with flying.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new DragonToken2(), 4), -7));
    }

    private SarkhanFireblood(final SarkhanFireblood card) {
        super(card);
    }

    @Override
    public SarkhanFireblood copy() {
        return new SarkhanFireblood(this);
    }
}

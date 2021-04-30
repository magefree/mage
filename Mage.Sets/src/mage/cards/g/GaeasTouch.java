
package mage.cards.g;

import mage.Mana;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class GaeasTouch extends CardImpl {

    private static final FilterCard filter = new FilterCard("a basic Forest card");

    static {
        filter.add(SuperType.BASIC.getPredicate());
        filter.add(SubType.FOREST.getPredicate());
    }

    public GaeasTouch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}{G}");

        // You may put a basic Forest card from your hand onto the battlefield. Activate this ability only any time you could cast a sorcery and only once each turn.
        LimitedTimesPerTurnActivatedAbility ability = new LimitedTimesPerTurnActivatedAbility(
                Zone.BATTLEFIELD, new PutCardFromHandOntoBattlefieldEffect(filter),
                new GenericManaCost(0), 1
        );
        ability.setTiming(TimingRule.SORCERY);
        addAbility(ability);

        // Sacrifice Gaea's Touch: Add {G}{G}.
        addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.GreenMana(2), new SacrificeSourceCost()));
    }

    private GaeasTouch(final GaeasTouch card) {
        super(card);
    }

    @Override
    public GaeasTouch copy() {
        return new GaeasTouch(this);
    }
}

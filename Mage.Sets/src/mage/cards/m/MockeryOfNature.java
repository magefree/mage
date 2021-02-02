
package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.EmergeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class MockeryOfNature extends CardImpl {

    public MockeryOfNature(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{9}");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Emerge {7}{G}
        this.addAbility(new EmergeAbility(this, new ManaCostsImpl<>("{7}{G}")));
        
        // When you cast Mockery of Nature, you may destroy target artifact or enchantment.
        Ability ability = new CastSourceTriggeredAbility(new DestroyTargetEffect(), true);
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.addAbility(ability);
    }

    private MockeryOfNature(final MockeryOfNature card) {
        super(card);
    }

    @Override
    public MockeryOfNature copy() {
        return new MockeryOfNature(this);
    }
}

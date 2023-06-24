
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author fireshoes
 */
public final class MagetaTheLion extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("creatures except for {this}");

    static {
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(AnotherPredicate.instance);
    }

    public MagetaTheLion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SPELLSHAPER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {2}{W}{W}, {tap}, Discard two cards: Destroy all creatures except for Mageta the Lion. Those creatures can't be regenerated.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyAllEffect(filter, true), new ManaCostsImpl<>("{2}{W}{W}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardTargetCost(new TargetCardInHand(2, StaticFilters.FILTER_CARD_CARDS)));
        this.addAbility(ability);
    }

    private MagetaTheLion(final MagetaTheLion card) {
        super(card);
    }

    @Override
    public MagetaTheLion copy() {
        return new MagetaTheLion(this);
    }
}

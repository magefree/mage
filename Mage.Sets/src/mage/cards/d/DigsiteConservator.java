package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.keyword.DiscoverEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInASingleGraveyard;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class DigsiteConservator extends CardImpl {

    public DigsiteConservator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");
        
        this.subtype.add(SubType.GNOME);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Sacrifice Digsite Conservator: Exile up to four target cards from a single graveyard. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new ExileTargetEffect(), new SacrificeSourceCost());
        ability.addTarget(new TargetCardInASingleGraveyard(0, 4, StaticFilters.FILTER_CARD_CARDS));
        this.addAbility(ability);

        // When Digsite Conservator dies, you may pay {4}. If you do, discover 4.
        this.addAbility(new DiesSourceTriggeredAbility(new DoIfCostPaid(
                new DiscoverEffect(4), new GenericManaCost(4)
        )));
    }

    private DigsiteConservator(final DigsiteConservator card) {
        super(card);
    }

    @Override
    public DigsiteConservator copy() {
        return new DigsiteConservator(this);
    }
}

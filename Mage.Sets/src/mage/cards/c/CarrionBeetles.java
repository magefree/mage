package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInASingleGraveyard;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class CarrionBeetles extends CardImpl {

    public CarrionBeetles(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {2}{B}, {T}: Exile up to three target cards from a single graveyard.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileTargetEffect(), new ManaCostsImpl<>("{2}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCardInASingleGraveyard(0, 3, StaticFilters.FILTER_CARD_CARDS));
        this.addAbility(ability);
    }

    private CarrionBeetles(final CarrionBeetles card) {
        super(card);
    }

    @Override
    public CarrionBeetles copy() {
        return new CarrionBeetles(this);
    }
}

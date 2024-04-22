package mage.cards.x;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.RemoveCounterCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.PopulateEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class XavierSalInfestedCaptain extends CardImpl {

    public XavierSalInfestedCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{G}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.FUNGUS);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {T}, Remove a counter from another permanent you control: Populate. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new PopulateEffect(), new TapSourceCost());
        ability.addCost(new RemoveCounterCost(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_PERMANENT)));
        this.addAbility(ability);

        // {T}, Sacrifice another creature: Proliferate. Activate only as a sorcery.
        Ability ability2 = new ActivateAsSorceryActivatedAbility(new ProliferateEffect(), new TapSourceCost());
        ability2.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE));
        this.addAbility(ability2);

    }

    private XavierSalInfestedCaptain(final XavierSalInfestedCaptain card) {
        super(card);
    }

    @Override
    public XavierSalInfestedCaptain copy() {
        return new XavierSalInfestedCaptain(this);
    }
}

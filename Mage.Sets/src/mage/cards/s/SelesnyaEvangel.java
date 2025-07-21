package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.SaprolingToken;

import java.util.UUID;

/**
 * @author Loki
 */
public final class SelesnyaEvangel extends CardImpl {

    public SelesnyaEvangel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {1}, {tap}, Tap an untapped creature you control: Create a 1/1 green Saproling creature token.
        Ability ability = new SimpleActivatedAbility(new CreateTokenEffect(new SaprolingToken()), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addCost(new TapTargetCost(StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURE));
        this.addAbility(ability);
    }

    private SelesnyaEvangel(final SelesnyaEvangel card) {
        super(card);
    }

    @Override
    public SelesnyaEvangel copy() {
        return new SelesnyaEvangel(this);
    }
}

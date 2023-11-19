package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.permanent.token.StitchersApprenticeHomunculusToken;

import java.util.UUID;

/**
 *
 * @author North
 */
public final class StitchersApprentice extends CardImpl {

    public StitchersApprentice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.HOMUNCULUS);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {1}{U}, {tap}: Create a 2/2 blue Homunculus creature token, then sacrifice a creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new StitchersApprenticeHomunculusToken()), new ManaCostsImpl<>("{1}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new SacrificeControllerEffect(StaticFilters.FILTER_PERMANENT_A_CREATURE, 1, ", then"));
        this.addAbility(ability);
    }

    private StitchersApprentice(final StitchersApprentice card) {
        super(card);
    }

    @Override
    public StitchersApprentice copy() {
        return new StitchersApprentice(this);
    }
}

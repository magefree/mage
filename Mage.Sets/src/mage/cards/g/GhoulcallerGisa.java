
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SacrificeCostCreaturesPower;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.permanent.token.ZombieToken;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class GhoulcallerGisa extends CardImpl {

    public GhoulcallerGisa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // {B}, {tap}, Sacrifice another creature: create X 2/2 black Zombie creature tokens, where X is the sacrificed creature's power.
        Effect effect = new CreateTokenEffect(new ZombieToken(), SacrificeCostCreaturesPower.instance);
        effect.setText("create X 2/2 black Zombie creature tokens, where X is the sacrificed creature's power");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{B}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE)));
        this.addAbility(ability);
    }

    private GhoulcallerGisa(final GhoulcallerGisa card) {
        super(card);
    }

    @Override
    public GhoulcallerGisa copy() {
        return new GhoulcallerGisa(this);
    }
}

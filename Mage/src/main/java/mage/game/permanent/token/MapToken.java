package mage.game.permanent.token;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.keyword.ExploreTargetEffect;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author Susucr
 */
public final class MapToken extends TokenImpl {

    public MapToken() {
        super("Map Token", "Map token");
        cardType.add(CardType.ARTIFACT);
        subtype.add(SubType.MAP);

        Ability ability = new ActivateAsSorceryActivatedAbility(
                new ExploreTargetEffect(false)
                        .setText("target creature you control explores"),
                new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost().setText("sacrifice this artifact"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    protected MapToken(final MapToken token) {
        super(token);
    }

    public MapToken copy() {
        return new MapToken(this);
    }
}

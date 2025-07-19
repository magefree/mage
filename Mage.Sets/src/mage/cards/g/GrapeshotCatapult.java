
package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author anonymous
 */
public final class GrapeshotCatapult extends CardImpl {

    public GrapeshotCatapult(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");
        this.subtype.add(SubType.CONSTRUCT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {tap}: Grapeshot Catapult deals 1 damage to target creature with flying.
        Ability activatedAbility = new SimpleActivatedAbility(new DamageTargetEffect(1), new TapSourceCost());
        activatedAbility.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_FLYING));
        this.addAbility(activatedAbility);
    }

    private GrapeshotCatapult(final GrapeshotCatapult card) {
        super(card);
    }

    @Override
    public GrapeshotCatapult copy() {
        return new GrapeshotCatapult(this);
    }
}

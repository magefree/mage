package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.YouControlABasicLandCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.keyword.EarthbendTargetEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetControlledLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BaSingSe extends CardImpl {

    public BaSingSe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // This land enters tapped unless you control a basic land.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(YouControlABasicLandCondition.instance)
                .addHint(YouControlABasicLandCondition.getHint()));

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());

        // {2}{G}, {T}: Earthbend 2. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new EarthbendTargetEffect(2), new ManaCostsImpl<>("{2}{G}")
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetControlledLandPermanent());
        this.addAbility(ability);
    }

    private BaSingSe(final BaSingSe card) {
        super(card);
    }

    @Override
    public BaSingSe copy() {
        return new BaSingSe(this);
    }
}

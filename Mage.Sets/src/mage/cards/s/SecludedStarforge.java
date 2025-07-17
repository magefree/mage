package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapVariableTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.permanent.token.RobotToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SecludedStarforge extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledArtifactPermanent("untapped artifacts you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public SecludedStarforge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {2}, {T}, Tap X untapped artifacts you control: Target creature gets +X/+0 until end of turn. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new BoostTargetEffect(GetXValue.instance, StaticValue.get(0)), new GenericManaCost(2)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new TapVariableTargetCost(filter));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // {5}, {T}: Create a 2/2 colorless Robot artifact creature token.
        ability = new SimpleActivatedAbility(new CreateTokenEffect(new RobotToken()), new GenericManaCost(5));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private SecludedStarforge(final SecludedStarforge card) {
        super(card);
    }

    @Override
    public SecludedStarforge copy() {
        return new SecludedStarforge(this);
    }
}

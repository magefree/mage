package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChillingChronicle extends CardImpl {

    public ChillingChronicle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "");

        this.transformable = true;
        this.nightCard = true;

        // {1}, {T}: Tap target nonland permanent. Transform Chilling Chronicle.
        Ability ability = new SimpleActivatedAbility(new TapTargetEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new TransformSourceEffect(false));
        ability.addTarget(new TargetNonlandPermanent());
        this.addAbility(ability);
    }

    private ChillingChronicle(final ChillingChronicle card) {
        super(card);
    }

    @Override
    public ChillingChronicle copy() {
        return new ChillingChronicle(this);
    }
}

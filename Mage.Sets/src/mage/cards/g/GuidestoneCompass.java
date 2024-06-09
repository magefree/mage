package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.keyword.ExploreTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class GuidestoneCompass extends CardImpl {

    public GuidestoneCompass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "");

        this.nightCard = true;
        this.color.setBlue(true);

        // {1}, {T}: Target creature you control explores. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new ExploreTargetEffect(),
                new GenericManaCost(1)
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private GuidestoneCompass(final GuidestoneCompass card) {
        super(card);
    }

    @Override
    public GuidestoneCompass copy() {
        return new GuidestoneCompass(this);
    }
}

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class SurlyFarrier extends CardImpl {

    public SurlyFarrier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.KITHKIN);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {T}: Target creature you control gets +1/+1 and gains vigilance until end of turn. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
            new BoostTargetEffect(1, 1).setText("target creature you control gets +1/+1"), 
            new TapSourceCost()
        );
        ability.addEffect(new GainAbilityTargetEffect(VigilanceAbility.getInstance()).setText("and gains vigilance until end of turn"));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_CREATURE));
        this.addAbility(ability);
    }

    private SurlyFarrier(final SurlyFarrier card) {
        super(card);
    }

    @Override
    public SurlyFarrier copy() {
        return new SurlyFarrier(this);
    }
}

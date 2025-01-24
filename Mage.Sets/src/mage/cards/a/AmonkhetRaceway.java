package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.MaxSpeedGainAbilityEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.StartYourEnginesAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AmonkhetRaceway extends CardImpl {

    public AmonkhetRaceway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Start your engines!
        this.addAbility(new StartYourEnginesAbility());

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // Max speed -- {T}: Target creature gains haste until end of turn.
        Ability ability = new SimpleActivatedAbility(new GainAbilityTargetEffect(HasteAbility.getInstance()), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(new SimpleStaticAbility(new MaxSpeedGainAbilityEffect(ability)));
    }

    private AmonkhetRaceway(final AmonkhetRaceway card) {
        super(card);
    }

    @Override
    public AmonkhetRaceway copy() {
        return new AmonkhetRaceway(this);
    }
}

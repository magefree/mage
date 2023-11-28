package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ArtifactYouControlCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.hint.common.ArtifactYouControlHint;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GeodeGrotto extends CardImpl {

    public GeodeGrotto(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.CAVE);
        this.nightCard = true;

        // {T}: Add {R}.
        this.addAbility(new RedManaAbility());

        // {2}{R}, {T}: Until end of turn, target creature gains haste and gets +X/+0, where X is the number of artifacts you control. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new GainAbilityTargetEffect(HasteAbility.getInstance())
                .setText("Until end of turn, target creature gains haste"), new ManaCostsImpl<>("{2}{R}"));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new BoostTargetEffect(
                ArtifactYouControlCount.instance, StaticValue.get(0)
        ).setText("and gets +X/+0, where X is the number of artifacts you control"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability.addHint(ArtifactYouControlHint.instance));
    }

    private GeodeGrotto(final GeodeGrotto card) {
        super(card);
    }

    @Override
    public GeodeGrotto copy() {
        return new GeodeGrotto(this);
    }
}

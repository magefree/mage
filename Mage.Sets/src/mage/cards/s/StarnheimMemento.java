package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StarnheimMemento extends CardImpl {

    public StarnheimMemento(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}: Add {W}.
        this.addAbility(new WhiteManaAbility());

        // {1}{W}, {T}: Target creature gets +1/+1 and gains flying until end of turn. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new BoostTargetEffect(1, 1)
                        .setText("target creature gets +1/+1"),
                new ManaCostsImpl<>("{1}{W}")
        );
        ability.addCost(new TapSourceCost());
        ability.addEffect(new GainAbilityTargetEffect(FlyingAbility.getInstance())
                .setText("and gains flying until end of turn"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private StarnheimMemento(final StarnheimMemento card) {
        super(card);
    }

    @Override
    public StarnheimMemento copy() {
        return new StarnheimMemento(this);
    }
}

package mage.cards.b;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author TheElk801
 */
public final class BasilicaSkullbomb extends CardImpl {

    public BasilicaSkullbomb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {1}, Sacrifice Basilica Skullbomb: Draw a card.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1), new GenericManaCost(1)
        );
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);

        // {2}{W}, Sacrifice Basilica Skullbomb: Target creature you control gets +2/+2 and gains flying until end of turn. Draw a card. Activate only as a sorcery.
        ability = new ActivateAsSorceryActivatedAbility(
                new BoostTargetEffect(2, 2)
                        .setText("target creature you control gets +2/+2"),
                new ManaCostsImpl<>("{2}{W}")
        );
        ability.addCost(new SacrificeSourceCost());
        ability.addEffect(new GainAbilityTargetEffect(FlyingAbility.getInstance())
                .setText("and gains flying until end of turn"));
        ability.addEffect(new DrawCardSourceControllerEffect(1));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private BasilicaSkullbomb(final BasilicaSkullbomb card) {
        super(card);
    }

    @Override
    public BasilicaSkullbomb copy() {
        return new BasilicaSkullbomb(this);
    }
}

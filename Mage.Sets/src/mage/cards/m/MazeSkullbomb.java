package mage.cards.m;

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
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author TheElk801
 */
public final class MazeSkullbomb extends CardImpl {

    public MazeSkullbomb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {1}, Sacrifice Maze Skullbomb: Draw a card.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1), new GenericManaCost(1)
        );
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);

        // {2}{G}, Sacrifice Maze Skullbomb: Target creature gets +3/+3 and gains trample until end of turn. Draw a card. Activate only as a sorcery.
        ability = new ActivateAsSorceryActivatedAbility(
                new BoostTargetEffect(3, 3)
                        .setText("target creature gets +3/+3"),
                new ManaCostsImpl<>("{2}{G}")
        );
        ability.addCost(new SacrificeSourceCost());
        ability.addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance())
                .setText("and gains trample until end of turn"));
        ability.addEffect(new DrawCardSourceControllerEffect(1));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private MazeSkullbomb(final MazeSkullbomb card) {
        super(card);
    }

    @Override
    public MazeSkullbomb copy() {
        return new MazeSkullbomb(this);
    }
}

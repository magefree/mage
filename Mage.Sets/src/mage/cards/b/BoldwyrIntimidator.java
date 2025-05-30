package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CowardsCantBlockWarriorsEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTypeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class BoldwyrIntimidator extends CardImpl {

    public BoldwyrIntimidator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{R}");
        this.subtype.add(SubType.GIANT, SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Cowards can't block Warriors.
        this.addAbility(new SimpleStaticAbility(new CowardsCantBlockWarriorsEffect()));

        // {R}: Target creature becomes a Coward until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new BecomesCreatureTypeTargetEffect(Duration.EndOfTurn, SubType.COWARD)
                        .setText("target creature becomes a Coward until end of turn"),
                new ManaCostsImpl<>("{R}")
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // {2}{R}: Target creature becomes a Warrior until end of turn.
        ability = new SimpleActivatedAbility(
                new BecomesCreatureTypeTargetEffect(Duration.EndOfTurn, SubType.WARRIOR)
                        .setText("target creature becomes a Warrior until end of turn"),
                new ManaCostsImpl<>("{2}{R}")
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private BoldwyrIntimidator(final BoldwyrIntimidator card) {
        super(card);
    }

    @Override
    public BoldwyrIntimidator copy() {
        return new BoldwyrIntimidator(this);
    }
}

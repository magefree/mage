package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesPlottedSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.PlotAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class AloeAlchemist extends CardImpl {

    public AloeAlchemist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Aloe Alchemist becomes plotted, target creature gets +3/+2 and gains trample until end of turn.
        Ability ability = new BecomesPlottedSourceTriggeredAbility(
                new BoostTargetEffect(3, 2, Duration.EndOfTurn)
                        .setText("target creature gets +3/+2")
        );
        ability.addTarget(new TargetCreaturePermanent());
        ability.addEffect(
                new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn)
                        .setText("and gains trample until end of turn")
        );
        this.addAbility(ability);

        // Plot {1}{G}
        this.addAbility(new PlotAbility("{1}{G}"));
    }

    private AloeAlchemist(final AloeAlchemist card) {
        super(card);
    }

    @Override
    public AloeAlchemist copy() {
        return new AloeAlchemist(this);
    }
}

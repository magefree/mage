package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.combat.CantBeBlockedByMoreThanOneSourceEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class VigorsporeWurm extends CardImpl {

    public VigorsporeWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}");

        this.subtype.add(SubType.WURM);
        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        // Undergrowth — When Vigorspore Wurm enters the battlefield, target creature gains vigilance and gets +X/+X until end of turn, where X is the number of creature cards in your graveyard.
        DynamicValue xValue = new CardsInControllerGraveyardCount(
                StaticFilters.FILTER_CARD_CREATURE
        );
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new GainAbilityTargetEffect(
                        VigilanceAbility.getInstance(),
                        Duration.EndOfTurn
                ).setText("target creature gains vigilance"),
                false);
        ability.addEffect(new BoostTargetEffect(
                xValue, xValue, Duration.EndOfTurn, true
        ).setText("and gets +X/+X until end of turn, "
                + "where X is the number of creature cards in your graveyard."));
        ability.addTarget(new TargetCreaturePermanent());
        ability.setAbilityWord(AbilityWord.UNDERGROWTH);
        this.addAbility(ability);

        // Vigorspore Wurm can't be blocked by more than one creature.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new CantBeBlockedByMoreThanOneSourceEffect()
        ));
    }

    private VigorsporeWurm(final VigorsporeWurm card) {
        super(card);
    }

    @Override
    public VigorsporeWurm copy() {
        return new VigorsporeWurm(this);
    }
}

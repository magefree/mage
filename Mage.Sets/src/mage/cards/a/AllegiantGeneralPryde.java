package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author Merlingilb
 */
public class AllegiantGeneralPryde extends CardImpl {
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Trooper creatures");

    static  {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(SubType.TROOPER.getPredicate());
    }

    public AllegiantGeneralPryde(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TROOPER);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Trooper creatures you control have "When this creature enters the battlefield, you may sacrifice a creature. If you do, draw two cards and lose 2 life."
        Ability gainedAbility = new EntersBattlefieldTriggeredAbility(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(2),
                new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT)
        ).addEffect(new LoseLifeSourceControllerEffect(2).concatBy("and")))
                .setTriggerPhrase("When this creature enters the battlefield, ");
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(gainedAbility, Duration.WhileOnBattlefield, filter)));
    }

    private AllegiantGeneralPryde(final AllegiantGeneralPryde card) {
        super(card);
    }

    @Override
    public AllegiantGeneralPryde copy() {
        return new AllegiantGeneralPryde(this);
    }
}

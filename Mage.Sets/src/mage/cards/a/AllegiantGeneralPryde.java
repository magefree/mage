package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetControlledCreaturePermanent;

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
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.TROOPER);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Trooper creatures you control have "When this creature enters the battlefield, you may sacrifice a creature. If you do, draw two cards and lose 2 life."
        SacrificeEffect sacrifceEffect = new SacrificeEffect(
                StaticFilters.FILTER_PERMANENT_CREATURE_CONTROLLED, 1, "");
        EntersBattlefieldTriggeredAbility ability = new EntersBattlefieldTriggeredAbility(sacrifceEffect, true, true);
        ability.setTriggerPhrase("When this creature enters the battlefield, ");
        ability.addTarget(new TargetControlledCreaturePermanent(1));
        DrawCardSourceControllerEffect drawCardSourceControllerEffect = new DrawCardSourceControllerEffect(2);
        drawCardSourceControllerEffect.setText("If you do, draw two cards");
        ability.addEffect(drawCardSourceControllerEffect);
        LoseLifeSourceControllerEffect loseLifeSourceControllerEffect = new LoseLifeSourceControllerEffect(2);
        loseLifeSourceControllerEffect.setText("and lose 2 life.");
        ability.addEffect(loseLifeSourceControllerEffect);
        //EntersBattlefieldTriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(2), true);
        GainAbilityControlledEffect effect = new GainAbilityControlledEffect(
                ability, Duration.WhileOnBattlefield, filter);
        this.addAbility(new SimpleStaticAbility(effect));
    }

    public AllegiantGeneralPryde(final AllegiantGeneralPryde card) {
        super(card);
    }

    @Override
    public Card copy() {
        return new AllegiantGeneralPryde(this);
    }
}

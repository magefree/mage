package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.PutCardIntoGraveFromAnywhereAllTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.TargetPlayerGainControlSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class MeasureOfWickedness extends CardImpl {

    private static final FilterCard filter = new FilterCard("another card");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public MeasureOfWickedness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{B}");


        // At the beginning of your end step, sacrifice Measure of Wickedness and you lose 8 life.        
        Ability ability = new BeginningOfEndStepTriggeredAbility(Zone.BATTLEFIELD, new SacrificeSourceEffect(), TargetController.YOU, null, false);
        Effect effect = new LoseLifeSourceControllerEffect(8);
        effect.setText("and you lose 8 life");
        ability.addEffect(effect);
        this.addAbility(ability);

        // Whenever another card is put into your graveyard from anywhere, target opponent gains control of Measure of Wickedness.
        ability = new PutCardIntoGraveFromAnywhereAllTriggeredAbility(
                new TargetPlayerGainControlSourceEffect(), false, filter, TargetController.YOU);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

    }

    private MeasureOfWickedness(final MeasureOfWickedness card) {
        super(card);
    }

    @Override
    public MeasureOfWickedness copy() {
        return new MeasureOfWickedness(this);
    }
}

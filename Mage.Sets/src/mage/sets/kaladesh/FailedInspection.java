package mage.sets.kaladesh;

import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

import java.util.UUID;

/**
 * Created by IGOUDT on 16-9-2016.
 */
public class FailedInspection extends CardImpl {

    public FailedInspection(final UUID ownerId){
        super(ownerId, 47, "Failed Inspection", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{2}{U}{U}");
        this.expansionSetCode = "KLD";

        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
        this.getSpellAbility().addEffect(new DiscardControllerEffect(1));
    }

    public FailedInspection(FailedInspection failedInspection){
        super(failedInspection);
    }

    public FailedInspection copy(){
        return new FailedInspection(this);
    }
}

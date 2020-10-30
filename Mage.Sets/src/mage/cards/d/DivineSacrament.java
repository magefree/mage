
package mage.cards.d;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author Beta_Steward (Honor of the Pure), LevelX2 (Demoralize), cbt
 */
public final class DivineSacrament extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("White creatures");
    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public DivineSacrament(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}{W}");


        // White creatures get +1/+1.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter, false));
        this.addAbility(ability);
        // Threshold - White creatures get an additional +1/+1 as long as seven or more cards are in your graveyard.
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                    new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter, false),
                    new CardsInControllerGraveyardCondition(7),
                    "If seven or more cards are in your graveyard, white creatures get an additional +1/+1."
                ));
        ability.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(ability);
    }

    public DivineSacrament(final DivineSacrament card) {
        super(card);
    }

    @Override
    public DivineSacrament copy() {
        return new DivineSacrament(this);
    }
}

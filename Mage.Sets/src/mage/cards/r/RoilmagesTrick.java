
package mage.cards.r;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ColorsOfManaSpentToCastCount;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class RoilmagesTrick extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures your opponents control");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public RoilmagesTrick(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{U}");

        // <i>Converge</i> &mdash; Creatures your opponents control get -X/-0 until end of turn, where X is the number of colors of mana spent to cast Roilmage's Trick.
        this.getSpellAbility().setAbilityWord(AbilityWord.CONVERGE);
        this.getSpellAbility().addEffect(new BoostAllEffect(
                new SignInversionDynamicValue(ColorsOfManaSpentToCastCount.getInstance()), StaticValue.get(-0), Duration.EndOfTurn, filter, false,
                "Creatures your opponents control get -X/-0 until end of turn, where X is the number of colors of mana spent to cast this spell.<br>", true));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
    }

    private RoilmagesTrick(final RoilmagesTrick card) {
        super(card);
    }

    @Override
    public RoilmagesTrick copy() {
        return new RoilmagesTrick(this);
    }
}


package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.continuous.ExchangeControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;

/**
 * @author LevelX2
 */
public final class ConjuredCurrency extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("permanent you neither own nor control");

    static {
        filter.add(TargetController.NOT_YOU.getOwnerPredicate());
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
    }
    private static final String rule = "you may exchange control of {this} and target permanent you neither own nor control";

    public ConjuredCurrency(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{5}{U}");

        this.color.setBlue(true);

        // At the beginning of your upkeep, you may exchange control of Conjured Currency and target permanent you neither own nor control.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new ExchangeControlTargetEffect(Duration.EndOfGame, rule, true), TargetController.YOU, true);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private ConjuredCurrency(final ConjuredCurrency card) {
        super(card);
    }

    @Override
    public ConjuredCurrency copy() {
        return new ConjuredCurrency(this);
    }
}

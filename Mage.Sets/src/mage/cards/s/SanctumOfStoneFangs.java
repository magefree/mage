package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfPreCombatMainTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 *
 * @author htrajan
 */
public final class SanctumOfStoneFangs extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("");

    static {
        filter.add(SubType.SHRINE.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public SanctumOfStoneFangs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");
        
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHRINE);

        // At the beginning of your precombat main phase, each opponent loses X life and you gain X life, where X is the number of Shrines you control.
        Ability ability = new BeginningOfPreCombatMainTriggeredAbility(
                new LoseLifeOpponentsEffect(new PermanentsOnBattlefieldCount(filter, null)).setText("each opponent loses X life"),
                TargetController.YOU, false);
        ability.addEffect(new GainLifeEffect(new PermanentsOnBattlefieldCount(filter)).setText("and you gain X life, where X is the number of Shrines you control"));
        this.addAbility(ability);
    }

    private SanctumOfStoneFangs(final SanctumOfStoneFangs card) {
        super(card);
    }

    @Override
    public SanctumOfStoneFangs copy() {
        return new SanctumOfStoneFangs(this);
    }
}

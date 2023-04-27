package mage.cards.d;

import java.util.UUID;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.permanent.token.SoldierLifelinkToken;

/**
 *
 * @author TheElk801
 */
public final class DawnOfHope extends CardImpl {

    public DawnOfHope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        // Whenever you gain life, you may pay {2}. If you do, draw a card.
        this.addAbility(new GainLifeControllerTriggeredAbility(
                new DoIfCostPaid(
                        new DrawCardSourceControllerEffect(1),
                        new GenericManaCost(2)), false
        ));

        // {3}{W}: Create a 1/1 white Soldier creature token with lifelink.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new CreateTokenEffect(new SoldierLifelinkToken()),
                new ManaCostsImpl<>("{3}{W}")
        ));
    }

    private DawnOfHope(final DawnOfHope card) {
        super(card);
    }

    @Override
    public DawnOfHope copy() {
        return new DawnOfHope(this);
    }
}

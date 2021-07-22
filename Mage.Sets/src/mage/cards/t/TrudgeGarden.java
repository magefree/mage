package mage.cards.t;

import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.FungusBeastToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TrudgeGarden extends CardImpl {

    public TrudgeGarden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // Whenever you gain life, you may pay {2}. If you do, create a 4/4 green Fungus Beast creature token with trample.
        this.addAbility(new GainLifeControllerTriggeredAbility(new DoIfCostPaid(
                new CreateTokenEffect(new FungusBeastToken()), new GenericManaCost(2)
        ), false));
    }

    private TrudgeGarden(final TrudgeGarden card) {
        super(card);
    }

    @Override
    public TrudgeGarden copy() {
        return new TrudgeGarden(this);
    }
}

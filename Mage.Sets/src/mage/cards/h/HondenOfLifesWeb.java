

package mage.cards.h;

import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.SpiritToken;

import java.util.UUID;

/**
 * @author Loki
 */
public final class HondenOfLifesWeb extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(
            new FilterControlledPermanent(SubType.SHRINE)
    );
    private static final Hint hint = new ValueHint("Shrines you control", xValue);

    public HondenOfLifesWeb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHRINE);

        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new CreateTokenEffect(new SpiritToken(), xValue), TargetController.YOU, false).addHint(hint));
    }

    private HondenOfLifesWeb(final HondenOfLifesWeb card) {
        super(card);
    }

    @Override
    public HondenOfLifesWeb copy() {
        return new HondenOfLifesWeb(this);
    }

}

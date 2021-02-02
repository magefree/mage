

package mage.cards.h;

import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
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

    static final FilterControlledPermanent filter = new FilterControlledPermanent("Shrine");

    static {
        filter.add(SubType.SHRINE.getPredicate());
    }

    public HondenOfLifesWeb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHRINE);

        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new CreateTokenEffect(new SpiritToken(), new PermanentsOnBattlefieldCount(filter)), TargetController.YOU, false));
    }

    private HondenOfLifesWeb(final HondenOfLifesWeb card) {
        super(card);
    }

    @Override
    public HondenOfLifesWeb copy() {
        return new HondenOfLifesWeb(this);
    }

}

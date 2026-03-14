package mage.cards.h;

import mage.abilities.dynamicvalue.common.ShrinesYouControlCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.SpiritToken;

import java.util.UUID;

/**
 * @author Loki
 */
public final class HondenOfLifesWeb extends CardImpl {

    public HondenOfLifesWeb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHRINE);

        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new CreateTokenEffect(new SpiritToken(), ShrinesYouControlCount.FOR_EACH)
        ).addHint(ShrinesYouControlCount.getHint()));
    }

    private HondenOfLifesWeb(final HondenOfLifesWeb card) {
        super(card);
    }

    @Override
    public HondenOfLifesWeb copy() {
        return new HondenOfLifesWeb(this);
    }

}

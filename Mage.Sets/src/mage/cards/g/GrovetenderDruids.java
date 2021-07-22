package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.AllyEntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.GrovetenderDruidsPlantToken;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class GrovetenderDruids extends CardImpl {

    public GrovetenderDruids(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // <i>Rally</i>-Whenever Grovetender Druids or another Ally enters the battlefield under your control, you may pay {1}.
        // If you do, create a 1/1 green Plant creature token.
        this.addAbility(new AllyEntersBattlefieldTriggeredAbility(new DoIfCostPaid(
                new CreateTokenEffect(new GrovetenderDruidsPlantToken()), new GenericManaCost(1)
        ), false));
    }

    private GrovetenderDruids(final GrovetenderDruids card) {
        super(card);
    }

    @Override
    public GrovetenderDruids copy() {
        return new GrovetenderDruids(this);
    }
}

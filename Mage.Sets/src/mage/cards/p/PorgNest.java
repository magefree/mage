package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SubType;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.permanent.token.PorgToken;

/**
 *
 * @author NinthWorld
 */
public final class PorgNest extends CardImpl {

    public PorgNest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // At the beginning of your upkeep, create a 0/1 green Bird creature token named Porg with "{G}: Monstrosity 1."
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new PorgToken()), TargetController.YOU, false));
    }

    private PorgNest(final PorgNest card) {
        super(card);
    }

    @Override
    public PorgNest copy() {
        return new PorgNest(this);
    }
}

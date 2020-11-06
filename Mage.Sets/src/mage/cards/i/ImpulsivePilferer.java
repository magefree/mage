package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.EncoreAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ImpulsivePilferer extends CardImpl {

    public ImpulsivePilferer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Impulsive Pilferer dies, create a Treasure token.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new TreasureToken())));

        // Encore {3}{R}
        this.addAbility(new EncoreAbility(new ManaCostsImpl<>("{3}{R}")));
    }

    private ImpulsivePilferer(final ImpulsivePilferer card) {
        super(card);
    }

    @Override
    public ImpulsivePilferer copy() {
        return new ImpulsivePilferer(this);
    }
}

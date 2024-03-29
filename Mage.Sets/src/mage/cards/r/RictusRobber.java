package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.common.MorbidHint;
import mage.abilities.keyword.PlotAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.ZombieRogueToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class RictusRobber extends CardImpl {

    public RictusRobber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When Rictus Robber enters the battlefield, if a creature died this turn, create a 2/2 blue and black Zombie Rogue creature token.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new ZombieRogueToken())),
                MorbidCondition.instance,
                "When {this} enters the battlefield, if a creature died this turn, create a 2/2 blue and black Zombie Rogue creature token."
        ).addHint(MorbidHint.instance));

        // Plot {2}{B}
        this.addAbility(new PlotAbility("{2}{B}"));
    }

    private RictusRobber(final RictusRobber card) {
        super(card);
    }

    @Override
    public RictusRobber copy() {
        return new RictusRobber(this);
    }
}

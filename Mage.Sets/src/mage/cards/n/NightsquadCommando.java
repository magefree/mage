package mage.cards.n;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.common.RaidHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.HumanSoldierToken;
import mage.watchers.common.PlayerAttackedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NightsquadCommando extends CardImpl {

    public NightsquadCommando(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Nightsquad Commando enters the battlefield, if you attacked this turn, create a 1/1 white Human Soldier creature token.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new HumanSoldierToken())),
                RaidCondition.instance, "When {this} enters the battlefield, " +
                "if you attacked this turn, create a 1/1 white Human Soldier creature token."
        ).addHint(RaidHint.instance), new PlayerAttackedWatcher());
    }

    private NightsquadCommando(final NightsquadCommando card) {
        super(card);
    }

    @Override
    public NightsquadCommando copy() {
        return new NightsquadCommando(this);
    }
}

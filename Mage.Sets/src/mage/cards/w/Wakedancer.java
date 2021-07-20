
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.common.MorbidHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.ZombieToken;

/**
 *
 * @author Loki
 */
public final class Wakedancer extends CardImpl {

    private static final String staticText = "<i>Morbid</i> &mdash; When {this} enters the battlefield, if a creature died this turn, create a 2/2 black Zombie creature token.";

    public Wakedancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);

        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // <i>Morbid</i> &mdash; When Wakedancer enters the battlefield, if a creature died this turn, create a 2/2 black Zombie creature token.
        TriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new ZombieToken()));
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, MorbidCondition.instance, staticText).addHint(MorbidHint.instance));
    }

    private Wakedancer(final Wakedancer card) {
        super(card);
    }

    @Override
    public Wakedancer copy() {
        return new Wakedancer(this);
    }
}

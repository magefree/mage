
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.AttackingCreatureCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.WingmateRocToken;
import mage.watchers.common.PlayerAttackedWatcher;

/**
 *
 * @author emerald000
 */
public final class WingmateRoc extends CardImpl {

    public WingmateRoc(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.subtype.add(SubType.BIRD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // <em>Raid</em> - When Wingmate Roc enters the battlefield, if you attacked with a creature this turn, create a 3/4 white Bird creature token with flying.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new WingmateRocToken())), RaidCondition.instance,
                "<i>Raid</i> &mdash; When {this} enters the battlefield, if you attacked with a creature this turn, create a 3/4 white Bird creature token with flying."),
                new PlayerAttackedWatcher());

        // Whenever Wingmate Roc attacks, you gain 1 life for each attacking creature.
        Effect effect = new GainLifeEffect(new AttackingCreatureCount());
        effect.setText("you gain 1 life for each attacking creature");
        this.addAbility(new AttacksTriggeredAbility(effect, false));
    }

    public WingmateRoc(final WingmateRoc card) {
        super(card);
    }

    @Override
    public WingmateRoc copy() {
        return new WingmateRoc(this);
    }
}

package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.AttackingCreatureCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.hint.common.RaidHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.WingmateRocToken;
import mage.watchers.common.PlayerAttackedWatcher;

import java.util.UUID;

/**
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

        // <i>Raid</i> &mdash; When Wingmate Roc enters the battlefield, if you attacked this turn, create a 3/4 white Bird creature token with flying.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new WingmateRocToken())), RaidCondition.instance,
                        "When {this} enters the battlefield, if you attacked this turn, create a 3/4 white Bird creature token with flying.")
                        .setAbilityWord(AbilityWord.RAID)
                        .addHint(RaidHint.instance),
                new PlayerAttackedWatcher());

        // Whenever Wingmate Roc attacks, you gain 1 life for each attacking creature.
        Effect effect = new GainLifeEffect(new AttackingCreatureCount());
        effect.setText("you gain 1 life for each attacking creature");
        this.addAbility(new AttacksTriggeredAbility(effect, false));
    }

    private WingmateRoc(final WingmateRoc card) {
        super(card);
    }

    @Override
    public WingmateRoc copy() {
        return new WingmateRoc(this);
    }
}

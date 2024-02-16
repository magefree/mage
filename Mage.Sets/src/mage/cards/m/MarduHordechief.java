package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.common.RaidHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.WarriorToken;
import mage.watchers.common.PlayerAttackedWatcher;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class MarduHordechief extends CardImpl {

    public MarduHordechief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // <i>Raid</i> &mdash; When Mardu Hordechief enters the battlefield, if you attacked this turn, create a 1/1 white Warrior creature token
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(new EntersBattlefieldTriggeredAbility(
                        new CreateTokenEffect(new WarriorToken())),
                        RaidCondition.instance,
                        "When {this} enters the battlefield, if you attacked this turn, create a 1/1 white Warrior creature token.")
                        .setAbilityWord(AbilityWord.RAID)
                        .addHint(RaidHint.instance),
                new PlayerAttackedWatcher());
    }

    private MarduHordechief(final MarduHordechief card) {
        super(card);
    }

    @Override
    public MarduHordechief copy() {
        return new MarduHordechief(this);
    }
}

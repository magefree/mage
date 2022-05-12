package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.RevoltCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.watchers.common.RevoltWatcher;

import java.util.UUID;

/**
 * @author JRHerlehy
 */
public final class SilkweaverElite extends CardImpl {

    public SilkweaverElite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Reach <i>(This creature can block creatures with flying.)
        this.addAbility(ReachAbility.getInstance());

        // <i>Revolt</i> &mdash; When Silkweaver Elite enters the battlefield, if a permanent you controlled left the battlefield this turn, draw a card.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1), false),
                RevoltCondition.instance, "When {this} enters the battlefield, " +
                "if a permanent you controlled left the battlefield this turn, draw a card."
        ).setAbilityWord(AbilityWord.REVOLT), new RevoltWatcher());
    }

    private SilkweaverElite(final SilkweaverElite card) {
        super(card);
    }

    @Override
    public SilkweaverElite copy() {
        return new SilkweaverElite(this);
    }
}

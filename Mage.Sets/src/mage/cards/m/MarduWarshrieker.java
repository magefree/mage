package mage.cards.m;

import mage.MageInt;
import mage.Mana;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.mana.AddManaToManaPoolSourceControllerEffect;
import mage.abilities.hint.common.RaidHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.watchers.common.PlayerAttackedWatcher;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class MarduWarshrieker extends CardImpl {

    public MarduWarshrieker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // <em>Raid</em> - When Mardu Warshrieker enters the battlefield, if you attacked this turn, add {R}{W}{B}.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(new EntersBattlefieldTriggeredAbility(
                        new AddManaToManaPoolSourceControllerEffect(new Mana(1, 0, 1, 1, 0, 0, 0, 0))),
                        RaidCondition.instance,
                        "When {this} enters the battlefield, if you attacked this turn, add {R}{W}{B}.")
                        .setAbilityWord(AbilityWord.RAID)
                        .addHint(RaidHint.instance),
                new PlayerAttackedWatcher());
    }

    private MarduWarshrieker(final MarduWarshrieker card) {
        super(card);
    }

    @Override
    public MarduWarshrieker copy() {
        return new MarduWarshrieker(this);
    }
}

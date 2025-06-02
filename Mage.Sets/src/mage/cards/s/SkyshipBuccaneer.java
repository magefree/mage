package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.common.RaidHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.watchers.common.PlayerAttackedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SkyshipBuccaneer extends CardImpl {

    public SkyshipBuccaneer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Raid -- When this creature enters, if you attacked this turn, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1))
                .withInterveningIf(RaidCondition.instance)
                .setAbilityWord(AbilityWord.RAID)
                .addHint(RaidHint.instance), new PlayerAttackedWatcher());
    }

    private SkyshipBuccaneer(final SkyshipBuccaneer card) {
        super(card);
    }

    @Override
    public SkyshipBuccaneer copy() {
        return new SkyshipBuccaneer(this);
    }
}

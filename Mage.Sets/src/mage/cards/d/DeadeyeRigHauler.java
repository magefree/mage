package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.hint.common.RaidHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.PlayerAttackedWatcher;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class DeadeyeRigHauler extends CardImpl {

    public DeadeyeRigHauler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // <i>Raid</i>— When Deadeye Rig-Hauler enters the battlefield, if you attacked this turn, you may return target creature to its owner's hand.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect(), true), RaidCondition.instance,
                "When {this} enters the battlefield, if you attacked this turn, you may return target creature to its owner's hand.");
        ability.addTarget(new TargetCreaturePermanent());
        ability.setAbilityWord(AbilityWord.RAID);
        ability.addHint(RaidHint.instance);
        this.addAbility(ability, new PlayerAttackedWatcher());
    }

    private DeadeyeRigHauler(final DeadeyeRigHauler card) {
        super(card);
    }

    @Override
    public DeadeyeRigHauler copy() {
        return new DeadeyeRigHauler(this);
    }
}

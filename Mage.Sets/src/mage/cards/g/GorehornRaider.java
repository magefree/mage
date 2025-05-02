package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.common.RaidHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;
import mage.watchers.common.PlayerAttackedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GorehornRaider extends CardImpl {

    public GorehornRaider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Raid -- When this creature enters, if you attacked this turn, this creature deals 2 damage to any target.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(2))
                .withRuleTextReplacement(false)
                .withInterveningIf(RaidCondition.instance);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability.setAbilityWord(AbilityWord.RAID).addHint(RaidHint.instance), new PlayerAttackedWatcher());
    }

    private GorehornRaider(final GorehornRaider card) {
        super(card);
    }

    @Override
    public GorehornRaider copy() {
        return new GorehornRaider(this);
    }
}

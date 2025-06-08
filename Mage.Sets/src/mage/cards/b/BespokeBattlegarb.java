package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CelebrationCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.watchers.common.PermanentsEnteredBattlefieldWatcher;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class BespokeBattlegarb extends CardImpl {

    public BespokeBattlegarb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{R}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+0.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(2, 0)));

        // Celebration -- At the beginning of combat on your turn, if two or more nonland permanents entered the battlefield under your control this turn, attach Bespoke Battlegarb to up to one target creature you control.
        Ability ability = new BeginningOfCombatTriggeredAbility(new AttachEffect(
                Outcome.BoostCreature, "attach {this} to up to one target creature you control"
        )).withInterveningIf(CelebrationCondition.instance);
        ability.addTarget(new TargetControlledCreaturePermanent(0, 1));
        ability.setAbilityWord(AbilityWord.CELEBRATION);
        ability.addHint(CelebrationCondition.getHint());
        this.addAbility(ability, new PermanentsEnteredBattlefieldWatcher());

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(2)));
    }

    private BespokeBattlegarb(final BespokeBattlegarb card) {
        super(card);
    }

    @Override
    public BespokeBattlegarb copy() {
        return new BespokeBattlegarb(this);
    }
}

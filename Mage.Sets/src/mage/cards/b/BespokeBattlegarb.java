package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CelebrationCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.watchers.common.CelebrationWatcher;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class BespokeBattlegarb extends CardImpl {

    public BespokeBattlegarb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{R}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(2, 0, Duration.WhileOnBattlefield)));

        // Celebration -- At the beginning of combat on your turn, if two or more nonland permanents entered the battlefield under your control this turn, attach Bespoke Battlegarb to up to one target creature you control.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfCombatTriggeredAbility(
                        new AttachEffect(
                                Outcome.BoostCreature,
                                "attach {this} to up to one target creature you control"
                        ),
                        TargetController.YOU,
                        false
                ), CelebrationCondition.instance, "At the beginning of combat on your turn, if two "
                + "or more nonland permanents entered the battlefield under your control this turn, "
                + "attach {this} to up to one target creature you control"
        );
        ability.addTarget(new TargetControlledCreaturePermanent(0, 1));
        ability.setAbilityWord(AbilityWord.CELEBRATION);
        ability.addHint(CelebrationCondition.getHint());
        this.addAbility(ability, new CelebrationWatcher());

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

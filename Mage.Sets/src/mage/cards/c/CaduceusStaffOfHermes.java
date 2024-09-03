package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.LifeCompareCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalPreventionEffect;
import mage.abilities.decorator.ConditionalReplacementEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PreventAllDamageToSourceEffect;
import mage.abilities.effects.common.PreventDamageToAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.constants.CardType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;


/**
 *
 * @author JvdB01
 */
public final class CaduceusStaffOfHermes extends CardImpl {

    public CaduceusStaffOfHermes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has lifelink.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(LifelinkAbility.getInstance(), AttachmentType.EQUIPMENT).setText("has lifelink"));
        // As long as you have 30 or more life, equipped creature gets +5/+5 and has indestructible and "Prevent all damage that would be dealt to this creature."
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                        new BoostEquippedEffect(5,5),
                        new LifeCompareCondition(TargetController.YOU, ComparisonType.OR_GREATER, 30),
                        "As long as you have 30 or more life, equipped creature gets +5/+5"
                        )
                    )
                );
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                        new GainAbilityAttachedEffect(IndestructibleAbility.getInstance().getInstance(), AttachmentType.EQUIPMENT),
                        new LifeCompareCondition(TargetController.YOU, ComparisonType.OR_GREATER, 30),
                        "and has indestructible"
                        )
                    )
                );
        this.addAbility(new SimpleStaticAbility(new ConditionalPreventionEffect(
                        new PreventDamageToAttachedEffect(Duration.WhileOnBattlefield, AttachmentType.EQUIPMENT, false),
                        new LifeCompareCondition(TargetController.YOU, ComparisonType.OR_GREATER, 30),
                        "and has \"Prevent all damage that would be dealt to this creature.\""
                        )
                    )
        );
        // Equip {W}{W}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new ManaCostsImpl<>("{W}{W}"), false));
    }

    private CaduceusStaffOfHermes(final CaduceusStaffOfHermes card) {
        super(card);
    }

    @Override
    public CaduceusStaffOfHermes copy() {
        return new CaduceusStaffOfHermes(this);
    }
}

enum CaduceusStaffOfHermesCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        return (controller.getLife() >= 30);
    }
}

package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainClassAbilitySourceEffect;
import mage.abilities.keyword.ClassLevelAbility;
import mage.abilities.keyword.ClassReminderAbility;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterEquipmentPermanent;
import mage.filter.predicate.permanent.EquippedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SwordToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.EachTargetPointer;

import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BlacksmithsTalent extends CardImpl {

    private static final FilterPermanent filter = new FilterEquipmentPermanent("equipment you control");
    private static final FilterPermanent filter2 = new FilterCreaturePermanent();

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter2.add(EquippedPredicate.instance);
    }

    public BlacksmithsTalent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}");

        this.subtype.add(SubType.CLASS);

        // (Gain the next level as a sorcery to add its ability.)
        this.addAbility(new ClassReminderAbility());

        // When Blacksmith's Talent enters, create a colorless Equipment artifact token named Sword with "Equipped creature gets +1/+1" and equip {2}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new SwordToken())));

        // {2}{R}: Level 2
        this.addAbility(new ClassLevelAbility(2, "{2}{R}"));

        // At the beginning of combat on your turn, attach target Equipment you control to up to one target creature you control.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new BlacksmithsTalentEffect()
        );
        ability.addTarget(new TargetPermanent(filter));
        ability.addTarget(new TargetControlledCreaturePermanent(0, 1));
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(ability, 2)));

        // {3}{R}: Level 3
        this.addAbility(new ClassLevelAbility(3, "{3}{R}"));

        // During your turn, equipped creatures you control have double strike and haste.
        ability = new SimpleStaticAbility(new ConditionalContinuousEffect(new GainAbilityControlledEffect(
                DoubleStrikeAbility.getInstance(), Duration.WhileOnBattlefield, filter2
        ), MyTurnCondition.instance, "during your turn, equipped creatures you control have double strike"));
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.WhileOnBattlefield
        ), MyTurnCondition.instance, "and haste"));
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(ability, 3)));
    }

    private BlacksmithsTalent(final BlacksmithsTalent card) {
        super(card);
    }

    @Override
    public BlacksmithsTalent copy() {
        return new BlacksmithsTalent(this);
    }
}

class BlacksmithsTalentEffect extends OneShotEffect {

    BlacksmithsTalentEffect() {
        super(Outcome.Benefit);
        this.setTargetPointer(new EachTargetPointer());
        staticText = "attach target Equipment you control to up to one target creature you control";
    }

    private BlacksmithsTalentEffect(final BlacksmithsTalentEffect effect) {
        super(effect);
    }

    @Override
    public BlacksmithsTalentEffect copy() {
        return new BlacksmithsTalentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<UUID> targets = this.getTargetPointer().getTargets(game, source);
        if (targets.size() < 2) {
            return false;
        }
        Permanent equipment = game.getPermanent(targets.get(0));
        Permanent creature = game.getPermanent(targets.get(1));
        return equipment != null && creature != null && creature.addAttachment(equipment.getId(), source, game);
    }
}

package mage.cards.e;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.YouDontLoseManaEffect;
import mage.abilities.effects.mana.AddManaToManaPoolSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.util.ManaUtil;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class ElectroAssaultingBattery extends CardImpl {

    public ElectroAssaultingBattery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // You don't lose unspent red mana as steps and phases end.
        this.addAbility(new SimpleStaticAbility(new YouDontLoseManaEffect(ManaType.RED)));

        // Whenever you cast an instant or sorcery spell, add {R}.
        this.addAbility(new SpellCastControllerTriggeredAbility(new AddManaToManaPoolSourceControllerEffect(Mana.RedMana(1)),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false));

        // When Electro leaves the battlefield, you may pay x. When you do, he deals X damage to target player.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new ElectroAssaultingBatteryEffect()));
    }

    private ElectroAssaultingBattery(final ElectroAssaultingBattery card) {
        super(card);
    }

    @Override
    public ElectroAssaultingBattery copy() {
        return new ElectroAssaultingBattery(this);
    }
}
class ElectroAssaultingBatteryEffect extends OneShotEffect {

    ElectroAssaultingBatteryEffect() {
        super(Outcome.Damage);
        staticText = "you may pay x. When you do, he deals X damage to target player";
    }

    private ElectroAssaultingBatteryEffect(final ElectroAssaultingBatteryEffect effect) {
        super(effect);
    }

    @Override
    public ElectroAssaultingBatteryEffect copy() {
        return new ElectroAssaultingBatteryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        if (controller.chooseUse(outcome, "Pay x mana to deal x damage to target player?", source, game)) {
            TargetPlayer target = new TargetPlayer();
            target.chooseTarget(outcome, controller.getId(), source, game);
            int amount = ManaUtil.playerPaysXGenericMana(false, "Electro, Assaulting Battery", controller, source, game);
            Player targetOpponent = game.getPlayer(target.getFirstTarget());
            if (targetOpponent != null) {
                targetOpponent.damage(amount, source, game);
            }
        }
        return true;
    }
}
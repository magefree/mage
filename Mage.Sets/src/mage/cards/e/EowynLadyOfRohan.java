package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.cost.AbilitiesCostReductionControllerEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.predicate.permanent.EquippedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EowynLadyOfRohan extends CardImpl {

    public EowynLadyOfRohan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // At the beginning of combat on your turn, target creature gains your choice of first strike or vigilance until end of turn. If that creature is equipped, it gains first strike and vigilance until end of turn instead.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new EowynLadyOfRohanEffect(), TargetController.YOU, false
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Equip abilities you activate cost {1} less to activate.
        this.addAbility(new SimpleStaticAbility(new AbilitiesCostReductionControllerEffect(
                EquipAbility.class, "Equip", 1
        ).setText("equip abilities you activate cost {1} less to activate")));
    }

    private EowynLadyOfRohan(final EowynLadyOfRohan card) {
        super(card);
    }

    @Override
    public EowynLadyOfRohan copy() {
        return new EowynLadyOfRohan(this);
    }
}

class EowynLadyOfRohanEffect extends OneShotEffect {

    EowynLadyOfRohanEffect() {
        super(Outcome.Benefit);
        staticText = "target creature gains your choice of first strike or vigilance until end of turn. " +
                "If that creature is equipped, it gains first strike and vigilance until end of turn instead";
    }

    private EowynLadyOfRohanEffect(final EowynLadyOfRohanEffect effect) {
        super(effect);
    }

    @Override
    public EowynLadyOfRohanEffect copy() {
        return new EowynLadyOfRohanEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        if (EquippedPredicate.instance.apply(permanent, game)) {
            game.addEffect(new GainAbilityTargetEffect(
                    FirstStrikeAbility.getInstance(), Duration.EndOfTurn
            ).setTargetPointer(new FixedTarget(permanent, game)), source);
            game.addEffect(new GainAbilityTargetEffect(
                    VigilanceAbility.getInstance(), Duration.EndOfTurn
            ).setTargetPointer(new FixedTarget(permanent, game)), source);
            return true;
        }
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Ability ability = player.chooseUse(
                outcome, "Choose first strike or vigilance", null,
                "First strike", "Vigilance", source, game
        ) ? FirstStrikeAbility.getInstance() : VigilanceAbility.getInstance();
        game.addEffect(new GainAbilityTargetEffect(ability, Duration.EndOfTurn)
                .setTargetPointer(new FixedTarget(permanent, game)), source);
        return true;
    }
}

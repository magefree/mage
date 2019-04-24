
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public final class MirriWeatherlightDuelist extends CardImpl {

    public MirriWeatherlightDuelist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // First Strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Whenever Mirri, Weatherlight Duelist attacks, each opponent can't block with more than one creature this combat.
        this.addAbility(new AttacksTriggeredAbility(new AddContinuousEffectToGame(new MirriWeatherlightDuelistBlockRestrictionEffect()), false));

        // As long as Mirri, Weatherlight Duelist is tapped, no more than one creature can attack you each combat.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new MirriWeatherlightDuelistAttackRestrictionEffect(1), SourceTappedCondition.instance,
                "As long as {this} is tapped, no more than one creature can attack you each combat."));
        this.addAbility(ability);
    }

    public MirriWeatherlightDuelist(final MirriWeatherlightDuelist card) {
        super(card);
    }

    @Override
    public MirriWeatherlightDuelist copy() {
        return new MirriWeatherlightDuelist(this);
    }
}

class MirriWeatherlightDuelistBlockRestrictionEffect extends RestrictionEffect {

    MirriWeatherlightDuelistBlockRestrictionEffect() {
        super(Duration.EndOfCombat);
        staticText = "each opponent can't block with more than one creature this combat";
    }

    MirriWeatherlightDuelistBlockRestrictionEffect(final MirriWeatherlightDuelistBlockRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public MirriWeatherlightDuelistBlockRestrictionEffect copy() {
        return new MirriWeatherlightDuelistBlockRestrictionEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game) {
        for (UUID creature : game.getCombat().getBlockers()) {
            if (game.getPlayer(game.getPermanent(creature).getOwnerId()).hasOpponent(attacker.getControllerId(), game)) {
                return false;
            }
        }
        return true;
    }
}

class MirriWeatherlightDuelistAttackRestrictionEffect extends ContinuousEffectImpl {

    private final int maxAttackedBy;

    public MirriWeatherlightDuelistAttackRestrictionEffect(int maxAttackedBy) {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        this.maxAttackedBy = maxAttackedBy;
        staticText = "No more than one creature can attack you each combat";
    }

    public MirriWeatherlightDuelistAttackRestrictionEffect(final MirriWeatherlightDuelistAttackRestrictionEffect effect) {
        super(effect);
        this.maxAttackedBy = effect.maxAttackedBy;
    }

    @Override
    public MirriWeatherlightDuelistAttackRestrictionEffect copy() {
        return new MirriWeatherlightDuelistAttackRestrictionEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        switch (layer) {
            case RulesEffects:
                Player controller = game.getPlayer(source.getControllerId());
                if (controller != null) {
                    // Change the rule
                    if (controller.getMaxAttackedBy() > maxAttackedBy) {
                        controller.setMaxAttackedBy(maxAttackedBy);
                    }
                }
                break;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }
}

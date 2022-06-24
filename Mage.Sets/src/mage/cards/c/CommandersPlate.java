package mage.cards.c;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterMana;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.CommanderPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.Set;
import java.util.UUID;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author TheElk801
 */
public final class CommandersPlate extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("commander");

    static {
        filter.add(CommanderPredicate.instance);
    }

    public CommandersPlate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +3/+3 and has protection from each color that's not in your commander's color identity.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(3, 3));
        ability.addEffect(new CommandersPlateEffect());
        this.addAbility(ability);

        // Equip commander {3}
        this.addAbility(new EquipAbility(
                Outcome.AddAbility, new GenericManaCost(3), new TargetPermanent(filter), false
        ));

        // Equip {5}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(5), new TargetControlledCreaturePermanent(), false));
    }

    private CommandersPlate(final CommandersPlate card) {
        super(card);
    }

    @Override
    public CommandersPlate copy() {
        return new CommandersPlate(this);
    }
}

class CommandersPlateEffect extends ContinuousEffectImpl {

    CommandersPlateEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.staticText = "has protection from each color that's not in your commander's color identity";
        this.generateGainAbilityDependencies(ProtectionAbility.from(new ObjectColor("WUBRG")), null);
        this.concatBy("and");
    }

    private CommandersPlateEffect(final CommandersPlateEffect effect) {
        super(effect);
    }

    @Override
    public CommandersPlateEffect copy() {
        return new CommandersPlateEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (!affectedObjectsSet) {
            return;
        }
        Permanent equipment = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (equipment == null || equipment.getAttachedTo() == null) {
            return;
        }
        this.setTargetPointer(new FixedTarget(equipment.getAttachedTo(), game));
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Permanent permanent = null;
        if (affectedObjectsSet) {
            permanent = game.getPermanent(targetPointer.getFirst(game, source));
            if (permanent == null) {
                discard();
                return true;
            }
        } else {
            Permanent equipment = game.getPermanent(source.getSourceId());
            if (equipment != null && equipment.getAttachedTo() != null) {
                permanent = game.getPermanentOrLKIBattlefield(equipment.getAttachedTo());
            }
        }
        Set<UUID> commanders = game.getCommandersIds(player, CommanderCardType.COMMANDER_OR_OATHBREAKER, false);
        if (commanders.isEmpty()) {
            return false;
        }
        ObjectColor color = new ObjectColor("WUBRG");
        for (UUID commanderId : commanders) {
            Card card = game.getCard(commanderId);
            if (card == null) {
                continue;
            }
            FilterMana identity = card.getColorIdentity();
            if (identity.isWhite()) {
                color.setWhite(false);
            }
            if (identity.isBlue()) {
                color.setBlue(false);
            }
            if (identity.isBlack()) {
                color.setBlack(false);
            }
            if (identity.isRed()) {
                color.setRed(false);
            }
            if (identity.isGreen()) {
                color.setGreen(false);
            }
        }
        if (permanent != null) {
            permanent.addAbility(ProtectionAbility.from(color), source.getSourceId(), game);
        }
        return true;
    }
}

package mage.cards.t;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.AttachedToMatchesFilterCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.UnattachCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityWithAttachmentEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacesCard;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePlayerOrPlaneswalker;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ToralfGodOfFury extends ModalDoubleFacesCard {

    private static final Condition condition
            = new AttachedToMatchesFilterCondition(StaticFilters.FILTER_PERMANENT_LEGENDARY);

    public ToralfGodOfFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.GOD}, "{2}{R}{R}",
                "Toralf's Hammer", new CardType[]{CardType.ARTIFACT}, new SubType[]{SubType.EQUIPMENT}, "{1}{R}"
        );

        // 1.
        // Toralf, God of Fury
        // Legendary Creature - God
        this.getLeftHalfCard().addSuperType(SuperType.LEGENDARY);
        this.getLeftHalfCard().setPT(new MageInt(5), new MageInt(4));

        // Trample
        this.getLeftHalfCard().addAbility(TrampleAbility.getInstance());

        // Whenever a creature or planeswalker an opponent controls is dealt excess noncombat damage, Toralf, God of Fury deals damage equal to the excess to any target other than that permanent.
        this.getLeftHalfCard().addAbility(new ToralfGodOfFuryTriggeredAbility());

        // 2.
        // Toralf's Hammer
        // Legendary Artifact - Equipment
        this.getRightHalfCard().addSuperType(SuperType.LEGENDARY);

        // Equipped creature has "{1}{R}, {T}, Unattach Toralf's Hammer: It deals 3 damage to any target. Return Toralf's Hammer to its owner's hand."
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new GainAbilityWithAttachmentEffect(
                "equipped creature has \"{1}{R}, {T}, Unattach {this}: It deals 3 damage to any target. Return {this} to its owner's hand.\"",
                new ToralfsHammerEffect(), new TargetAnyTarget(), new UnattachCost(), new ManaCostsImpl<>("{1}{R}"), new TapSourceCost()
        )));

        // Equipped creature get +3/+0 as long as it's legendary.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostEquippedEffect(3, 0), condition,
                "equipped creature gets +3/+0 as long as it's legendary"
        )));

        // Equip {1}{R}
        this.getRightHalfCard().addAbility(new EquipAbility(Outcome.BoostCreature, new ManaCostsImpl<>("{1}{R}"), false));
    }

    private ToralfGodOfFury(final ToralfGodOfFury card) {
        super(card);
    }

    @Override
    public ToralfGodOfFury copy() {
        return new ToralfGodOfFury(this);
    }
}

class ToralfGodOfFuryTriggeredAbility extends TriggeredAbilityImpl {

    ToralfGodOfFuryTriggeredAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    private ToralfGodOfFuryTriggeredAbility(final ToralfGodOfFuryTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedEvent dEvent = (DamagedEvent) event;
        if (dEvent.getExcess() < 1
                || dEvent.isCombatDamage()
                || !game.getOpponents(getControllerId()).contains(game.getControllerId(event.getTargetId()))) {
            return false;
        }
        this.getEffects().clear();
        this.getTargets().clear();
        int excessDamage = dEvent.getExcess();
        this.addEffect(new DamageTargetEffect(excessDamage));
        FilterCreaturePlayerOrPlaneswalker filter = new FilterCreaturePlayerOrPlaneswalker();
        filter.getPermanentFilter().add(Predicates.not(new MageObjectReferencePredicate(new MageObjectReference(event.getTargetId(), game))));
        this.addTarget(new TargetAnyTarget(filter).withChooseHint(Integer.toString(excessDamage) + " damage"));
        return true;
    }

    @Override
    public ToralfGodOfFuryTriggeredAbility copy() {
        return new ToralfGodOfFuryTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a creature or planeswalker an opponent controls is dealt excess noncombat damage, " +
                "{this} deals damage equal to the excess to any target other than that permanent.";
    }
}

class ToralfsHammerEffect extends OneShotEffect {

    ToralfsHammerEffect() {
        super(Outcome.Benefit);
    }

    private ToralfsHammerEffect(final ToralfsHammerEffect effect) {
        super(effect);
    }

    @Override
    public ToralfsHammerEffect copy() {
        return new ToralfsHammerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Object object = getValue("attachedPermanent");
        Player player = game.getPlayer(source.getControllerId());
        if (!(object instanceof Permanent) || player == null) {
            return false;
        }
        Permanent permanent = (Permanent) object;
        Permanent targetedPermanent = game.getPermanent(source.getFirstTarget());
        if (targetedPermanent == null) {
            Player targetedPlayer = game.getPlayer(source.getFirstTarget());
            if (targetedPlayer != null) {
                targetedPlayer.damage(3, permanent.getId(), source, game);
            }
        } else {
            targetedPermanent.damage(3, permanent.getId(), source, game);
        }
        player.moveCards(permanent, Zone.HAND, source, game);
        return true;
    }

    @Override
    public String getText(Mode mode) {
        String name = "Toralf's Hammer";
        Object object = getValue("attachedPermanent");
        if (object instanceof Permanent) {
            name = ((Permanent) object).getName();
        }
        return "It deals 3 damage to any target. Return " + name + " to its owner's hand.";
    }
}

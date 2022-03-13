package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.ReconfigureAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KomainuBattleArmor extends CardImpl {

    public KomainuBattleArmor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.EQUIPMENT);
        this.subtype.add(SubType.DOG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Equipped creature gets +2/+2 and has menace.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(2, 2));
        ability.addEffect(new GainAbilityAttachedEffect(
                new MenaceAbility(false), AttachmentType.EQUIPMENT
        ).setText("and has menace"));
        this.addAbility(ability);

        // Whenever Komainu Battle Armor or equipped creature deals combat damage to a player, goad each creature that player controls.
        this.addAbility(new KomainuBattleArmorTriggeredAbility());

        // Reconfigure {4}
        this.addAbility(new ReconfigureAbility("{4}"));
    }

    private KomainuBattleArmor(final KomainuBattleArmor card) {
        super(card);
    }

    @Override
    public KomainuBattleArmor copy() {
        return new KomainuBattleArmor(this);
    }
}

class KomainuBattleArmorTriggeredAbility extends TriggeredAbilityImpl {

    KomainuBattleArmorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new KomainuBattleArmorEffect());
    }

    private KomainuBattleArmorTriggeredAbility(final KomainuBattleArmorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public KomainuBattleArmorTriggeredAbility copy() {
        return new KomainuBattleArmorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return false;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!((DamagedEvent) event).isCombatDamage()) {
            return false;
        }
        if (getSourceId().equals(event.getSourceId())) {
            getEffects().setTargetPointer(new FixedTarget(event.getTargetId()));
            return true;
        }
        Permanent permanent = getSourcePermanentOrLKI(game);
        if (permanent != null && event.getSourceId().equals(permanent.getAttachedTo())) {
            getEffects().setTargetPointer(new FixedTarget(event.getTargetId()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} or equipped creature deals combat damage to a player, goad each creature that player controls.";
    }
}

class KomainuBattleArmorEffect extends OneShotEffect {

    KomainuBattleArmorEffect() {
        super(Outcome.Benefit);
    }

    private KomainuBattleArmorEffect(final KomainuBattleArmorEffect effect) {
        super(effect);
    }

    @Override
    public KomainuBattleArmorEffect copy() {
        return new KomainuBattleArmorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID playerId = getTargetPointer().getFirst(game, source);
        if (playerId == null) {
            return false;
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_CREATURE,
                playerId, source, game
        )) {
            game.addEffect(new GoadTargetEffect().setTargetPointer(new FixedTarget(permanent, game)), source);
        }
        return true;
    }
}


package mage.cards.k;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterBlockingCreature;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class KusariGama extends CardImpl {

    public KusariGama(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has "{2}: This creature gets +1/+0 until end of turn."
        Ability gainedAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 0, Duration.EndOfTurn), new GenericManaCost(2));
        Effect effect = new GainAbilityAttachedEffect(gainedAbility, AttachmentType.EQUIPMENT);
        effect.setText("Equipped creature has \"{2}: This creature gets +1/+0 until end of turn.\"");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        this.addAbility(ability);
        // Whenever equipped creature deals damage to a blocking creature, Kusari-Gama deals that much damage to each other creature defending player controls.
        this.addAbility(new KusariGamaAbility());
        // Equip {3}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(3)));
    }

    private KusariGama(final KusariGama card) {
        super(card);
    }

    @Override
    public KusariGama copy() {
        return new KusariGama(this);
    }
}

class KusariGamaAbility extends TriggeredAbilityImpl {

    private static final FilterCreaturePermanent filter = new FilterBlockingCreature();

    public KusariGamaAbility() {
        super(Zone.BATTLEFIELD, new KusariGamaDamageEffect());
    }

    private KusariGamaAbility(final KusariGamaAbility ability) {
        super(ability);
    }

    @Override
    public KusariGamaAbility copy() {
        return new KusariGamaAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent sourcePermanet = game.getPermanent(event.getSourceId());
        Permanent targetPermanet = game.getPermanent(event.getTargetId());
        if (sourcePermanet != null && targetPermanet != null && sourcePermanet.getAttachments().contains(this.getSourceId()) && filter.match(targetPermanet, game)) {
            this.getEffects().get(0).setValue("damageAmount", event.getAmount());
            this.getEffects().get(0).setValue("damagedCreatureId", targetPermanet.getId());
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever equipped creature deals damage to a blocking creature, {this} deals that much damage to each other creature defending player controls.";
    }
}

class KusariGamaDamageEffect extends OneShotEffect {

    public KusariGamaDamageEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals that much damage to each other creature defending player controls";
    }

    private KusariGamaDamageEffect(final KusariGamaDamageEffect effect) {
        super(effect);
    }

    @Override
    public KusariGamaDamageEffect copy() {
        return new KusariGamaDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Integer damage = (Integer) this.getValue("damageAmount");
        if (damage != null && damage > 0) {
            UUID damagedCreatureId = (UUID) this.getValue("damagedCreatureId");
            Permanent creature = game.getPermanent(damagedCreatureId);
            if (creature == null) {
                creature = (Permanent) game.getLastKnownInformation(damagedCreatureId, Zone.BATTLEFIELD);
            }
            if (creature != null) {
                for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, creature.getControllerId(), game)) {
                    if (!permanent.getId().equals(damagedCreatureId)) {
                        permanent.damage(damage, source.getSourceId(), source, game, false, true);
                    }
                }
            }
            return true;
        }
        return false;
    }
}

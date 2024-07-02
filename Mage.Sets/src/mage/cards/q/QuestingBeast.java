package mage.cards.q;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToOpponentTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.DauntAbility;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterPlaneswalkerPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.PreventDamageEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetPlaneswalkerPermanent;
import mage.target.targetadjustment.DamagedPlayerControlsTargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801, notgreat
 */
public final class QuestingBeast extends CardImpl {
    private static final FilterPlaneswalkerPermanent filter = new FilterPlaneswalkerPermanent("planeswalker that player controls");

    public QuestingBeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Questing Beast can't be blocked by creatures with power 2 or less.
        this.addAbility(new DauntAbility());

        // Combat damage that would be dealt by creatures you control can't be prevented.
        this.addAbility(new SimpleStaticAbility(new QuestingBeastPreventionEffect()));

        // Whenever Questing Beast deals combat damage to an opponent, it deals that much damage to target planeswalker that player controls.
        Ability ability = new DealsDamageToOpponentTriggeredAbility(new DamageTargetEffect(SavedDamageValue.MUCH), false, true, true);
        ability.addTarget(new TargetPlaneswalkerPermanent(filter));
        ability.setTargetAdjuster(new DamagedPlayerControlsTargetAdjuster());
        this.addAbility(ability);
    }

    private QuestingBeast(final QuestingBeast card) {
        super(card);
    }

    @Override
    public QuestingBeast copy() {
        return new QuestingBeast(this);
    }
}

class QuestingBeastPreventionEffect extends ContinuousRuleModifyingEffectImpl {

    QuestingBeastPreventionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Combat damage that would be dealt by creatures you control can't be prevented";
    }

    private QuestingBeastPreventionEffect(final QuestingBeastPreventionEffect effect) {
        super(effect);
    }

    @Override
    public QuestingBeastPreventionEffect copy() {
        return new QuestingBeastPreventionEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PREVENT_DAMAGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!((PreventDamageEvent) event).isCombatDamage()) {
            return false;
        }
        Permanent permanent = game.getPermanent(event.getSourceId());
        return permanent != null
                && permanent.isCreature(game)
                && permanent.isControlledBy(source.getControllerId());
    }
}

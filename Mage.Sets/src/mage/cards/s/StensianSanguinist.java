package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.BecomePreparedSourceEffect;
import mage.abilities.effects.common.LoseLifeOpponentsYouGainLifeLostEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class StensianSanguinist extends PrepareCard {

    public StensianSanguinist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}", "Exsanguinate", CardType.SORCERY, "{X}{B}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you attack, target creature gains deathtouch until end of turn. Whenever that creature deals combat damage to a player this combat, this creature becomes prepared.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(new GainAbilityTargetEffect(DeathtouchAbility.getInstance()), 1);
        ability.addEffect(new StensianSanguinistEffect());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Exsanguinate
        // Sorcery {X}{B}{B}
        // Each opponent loses X life. You gain life equal to the life lost this way.
        this.getSpellCard().getSpellAbility().addEffect(new LoseLifeOpponentsYouGainLifeLostEffect(GetXValue.instance, "X life"));
    }

    private StensianSanguinist(final StensianSanguinist card) {
        super(card);
    }

    @Override
    public StensianSanguinist copy() {
        return new StensianSanguinist(this);
    }
}

class StensianSanguinistEffect extends OneShotEffect {

    StensianSanguinistEffect() {
        super(Outcome.Benefit);
        this.staticText = "Whenever that creature deals combat damage to a player this combat, {this} becomes prepared";
    }

    private StensianSanguinistEffect(final StensianSanguinistEffect effect) {
        super(effect);
    }

    @Override
    public StensianSanguinistEffect copy() {
        return new StensianSanguinistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        game.addDelayedTriggeredAbility(new StensianSanguinistDelayedTriggeredAbility(
                new MageObjectReference(permanent, game)
        ), source);
        return true;
    }
}

class StensianSanguinistDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private final MageObjectReference mor;

    StensianSanguinistDelayedTriggeredAbility(MageObjectReference mor) {
        super(new BecomePreparedSourceEffect(), Duration.EndOfCombat, false, false);
        this.mor = mor;
    }

    private StensianSanguinistDelayedTriggeredAbility(final StensianSanguinistDelayedTriggeredAbility ability) {
        super(ability);
        this.mor = ability.mor;
    }

    @Override
    public StensianSanguinistDelayedTriggeredAbility copy() {
        return new StensianSanguinistDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((DamagedPlayerEvent) event).isCombatDamage()) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
            return mor.refersTo(permanent, game);
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever that creature deals combat damage to a player this combat, {this} becomes prepared.";
    }
}

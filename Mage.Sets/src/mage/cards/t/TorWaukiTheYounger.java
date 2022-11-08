package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.target.common.TargetAnyTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TorWaukiTheYounger extends CardImpl {

    public TorWaukiTheYounger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // If another source you control would deal noncombat damage to a permanent or player, it deals that much damage plus 1 to that permanent or player instead.
        this.addAbility(new SimpleStaticAbility(new TorWaukiTheYoungerEffect()));

        // Whenever you cast an instant or sorcery spell, Tor Wauki the Younger deals 2 damage to any target.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new DamageTargetEffect(2),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        );
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private TorWaukiTheYounger(final TorWaukiTheYounger card) {
        super(card);
    }

    @Override
    public TorWaukiTheYounger copy() {
        return new TorWaukiTheYounger(this);
    }
}

class TorWaukiTheYoungerEffect extends ReplacementEffectImpl {

    TorWaukiTheYoungerEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "if another source you control would deal noncombat damage to a permanent or player, " +
                "it deals that much damage plus 1 to that permanent or player instead";
    }

    TorWaukiTheYoungerEffect(final TorWaukiTheYoungerEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PERMANENT
                || event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.isControlledBy(game.getControllerId(event.getSourceId()))
                && !source.getSourceId().equals(event.getSourceId())
                && !((DamageEvent) event).isCombatDamage();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowInc(event.getAmount(), 1));
        return false;
    }

    @Override
    public TorWaukiTheYoungerEffect copy() {
        return new TorWaukiTheYoungerEffect(this);
    }
}

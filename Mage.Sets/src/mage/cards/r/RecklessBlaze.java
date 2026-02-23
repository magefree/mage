package mage.cards.r;

import mage.MageObjectReference;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RecklessBlaze extends CardImpl {

    public RecklessBlaze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");

        this.subtype.add(SubType.LESSON);

        // Reckless Blaze deals 5 damage to each creature. Whenever a creature you control dealt damage this way dies this turn, add {R}.
        this.getSpellAbility().addEffect(new RecklessBlazeEffect());
    }

    private RecklessBlaze(final RecklessBlaze card) {
        super(card);
    }

    @Override
    public RecklessBlaze copy() {
        return new RecklessBlaze(this);
    }
}

class RecklessBlazeEffect extends OneShotEffect {

    RecklessBlazeEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals 5 damage to each creature. " +
                "Whenever a creature you control dealt damage this way dies this turn, add {R}";
    }

    private RecklessBlazeEffect(final RecklessBlazeEffect effect) {
        super(effect);
    }

    @Override
    public RecklessBlazeEffect copy() {
        return new RecklessBlazeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<MageObjectReference> morSet = new HashSet<>();
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), source, game
        )) {
            if (permanent.damage(5, source, game) > 0) {
                morSet.add(new MageObjectReference(permanent, game));
            }
        }
        game.addDelayedTriggeredAbility(new RecklessBlazeTriggeredAbility(morSet), source);
        return true;
    }
}

class RecklessBlazeTriggeredAbility extends DelayedTriggeredAbility {

    private final Set<MageObjectReference> morSet = new HashSet<>();

    RecklessBlazeTriggeredAbility(Set<MageObjectReference> morSet) {
        super(new BasicManaEffect(Mana.RedMana(1)), Duration.EndOfTurn, false, false);
        this.morSet.addAll(morSet);
        this.setTriggerPhrase("Whenever a creature you control dealt damage this way dies this turn, ");
    }

    private RecklessBlazeTriggeredAbility(final RecklessBlazeTriggeredAbility ability) {
        super(ability);
        this.morSet.addAll(ability.morSet);
    }

    @Override
    public RecklessBlazeTriggeredAbility copy() {
        return new RecklessBlazeTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        return zEvent.isDiesEvent()
                && zEvent.getTarget() != null
                && morSet.stream().anyMatch(mor -> mor.refersTo(zEvent.getTarget(), game))
                && zEvent.getTarget().isControlledBy(getControllerId())
                && zEvent.getTarget().isCreature(game);
    }
}

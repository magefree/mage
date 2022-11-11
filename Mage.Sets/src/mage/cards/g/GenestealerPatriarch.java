package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.DefendingPlayerControlsPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class GenestealerPatriarch extends CardImpl {

    public static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature defending player controls");

    static {
        filter.add(DefendingPlayerControlsPredicate.instance);
    }

    public GenestealerPatriarch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");
        this.subtype.add(SubType.TYRANID);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Genestealer's Kiss — Whenever Genestealer Patriarch attacks, put an infection counter on target creature defending player controls.
        Ability ability = new AttacksTriggeredAbility(new AddCountersTargetEffect(CounterType.INFECTION.createInstance())).withFlavorWord("Genestealer's Kiss");
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // Children of the Cult — Whenever a creature with an infection counter on it dies, you create
        // a token that's a copy of that creature, except it's a Tyranid in addition to its other types.
        this.addAbility(new GenestealerPatriarchTriggeredAbility().withFlavorWord("Children of the Cult"));
    }

    private GenestealerPatriarch(final GenestealerPatriarch card) {
        super(card);
    }

    @Override
    public GenestealerPatriarch copy() {
        return new GenestealerPatriarch(this);
    }
}

class GenestealerPatriarchTriggeredAbility extends TriggeredAbilityImpl {

    public GenestealerPatriarchTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GenestealerPatriarchCloneEffect());
    }

    public GenestealerPatriarchTriggeredAbility(GenestealerPatriarchTriggeredAbility ability) {
        super(ability);
        setTriggerPhrase("Whenever a creature with an infection counter on it dies, ");
    }

    @Override
    public GenestealerPatriarchTriggeredAbility copy() {
        return new GenestealerPatriarchTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.isDiesEvent()) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(zEvent.getTargetId());
            if (permanent != null
                    && permanent.isCreature(game)
                    && permanent.getCounters(game).containsKey(CounterType.INFECTION)) {
                this.getEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
                return true;
            }
        }
        return false;
    }
}

class GenestealerPatriarchCloneEffect extends OneShotEffect {

    public GenestealerPatriarchCloneEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "you create a token that's a copy of that creature, except it's a Tyranid in addition to its other types";
    }

    public GenestealerPatriarchCloneEffect(final GenestealerPatriarchCloneEffect effect) {
        super(effect);
    }

    @Override
    public GenestealerPatriarchCloneEffect copy() {
        return new GenestealerPatriarchCloneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card creature = game.getCard(getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (creature != null && controller != null) {
            CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(source.getControllerId());
            effect.setTargetPointer(new FixedTarget(creature, game));
            effect.setAdditionalSubType(SubType.TYRANID);
            effect.apply(game, source);
            return true;
        }
        return false;
    }
}

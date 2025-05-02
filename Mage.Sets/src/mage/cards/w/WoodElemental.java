package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetSacrifice;

import java.util.UUID;

/**
 *
 * @author L_J
 */
public final class WoodElemental extends CardImpl {

    public WoodElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // As Wood Elemental enters the battlefield, sacrifice any number of untapped Forests.
        this.addAbility(new AsEntersBattlefieldAbility(new WoodElementalEffect()));

        // Wood Elemental's power and toughness are each equal to the number of Forests sacrificed as it entered the battlefield.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new InfoEffect("{this}'s power and toughness are each equal to the number of Forests sacrificed as it entered the battlefield")));
    }

    private WoodElemental(final WoodElemental card) {
        super(card);
    }

    @Override
    public WoodElemental copy() {
        return new WoodElemental(this);
    }
}

class WoodElementalEffect extends ReplacementEffectImpl {
    
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("untapped Forests you control");
    
    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(SubType.FOREST.getPredicate());
    }

    public WoodElementalEffect() {
        super(Duration.EndOfGame, Outcome.Sacrifice);
        staticText = "sacrifice any number of untapped Forests";
    }

    private WoodElementalEffect(final WoodElementalEffect effect) {
        super(effect);
    }

    @Override
    public WoodElementalEffect copy() {
        return new WoodElementalEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId().equals(source.getSourceId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        Player controller = game.getPlayer(source.getControllerId());
        if (creature == null || controller == null) {
            return false;
        }
        Target target = new TargetSacrifice(0, Integer.MAX_VALUE, filter);
        if (!target.canChoose(source.getControllerId(), source, game)) {
            return false;
        }
        controller.choose(Outcome.Detriment, target, source, game);
        if (target.getTargets().isEmpty()) {
            return false;
        }
        int value = 0;
        for (UUID targetId : target.getTargets()) {
            Permanent targetCreature = game.getPermanent(targetId);
            if (targetCreature != null && targetCreature.sacrifice(source, game)) {
                value++;
            }
        }
        game.addEffect(new SetBasePowerToughnessSourceEffect(value, value, Duration.WhileOnBattlefield), source);
        this.discard(); // prevent multiple replacements e.g. on blink
        return false;
    }
}

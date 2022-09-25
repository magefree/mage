
package mage.cards.i;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.util.functions.EmptyCopyApplier;

/**
 *
 * @author noxx
 */
public final class InfiniteReflection extends CardImpl {

    public InfiniteReflection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{5}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Copy));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // When Infinite Reflection enters the battlefield attached to a creature, each other nontoken creature you control becomes a copy of that creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new InfiniteReflectionTriggeredEffect()));

        // Nontoken creatures you control enter the battlefield as a copy of enchanted creature.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new InfiniteReflectionEntersBattlefieldEffect()));
    }

    private InfiniteReflection(final InfiniteReflection card) {
        super(card);
    }

    @Override
    public InfiniteReflection copy() {
        return new InfiniteReflection(this);
    }
}

class InfiniteReflectionTriggeredEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    public InfiniteReflectionTriggeredEffect() {
        super(Outcome.Sacrifice);
        this.staticText = " attached to a creature, each other nontoken creature you control becomes a copy of that creature";
    }

    public InfiniteReflectionTriggeredEffect(final InfiniteReflectionTriggeredEffect effect) {
        super(effect);
    }

    @Override
    public InfiniteReflectionTriggeredEffect copy() {
        return new InfiniteReflectionTriggeredEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null && sourcePermanent.getAttachedTo() != null) {
            Permanent toCopyFromPermanent = game.getPermanent(sourcePermanent.getAttachedTo());
            if (toCopyFromPermanent != null) {
                for (Permanent toCopyToPermanent : game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {
                    if (!toCopyToPermanent.equals(toCopyFromPermanent) && !(toCopyToPermanent instanceof PermanentToken)) {
                        game.copyPermanent(toCopyFromPermanent, toCopyToPermanent.getId(), source, new EmptyCopyApplier());
                    }
                }
                return true;
            }
        }
        return false;
    }
}

class InfiniteReflectionEntersBattlefieldEffect extends ReplacementEffectImpl {

    public InfiniteReflectionEntersBattlefieldEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        this.staticText = "Nontoken creatures you control enter the battlefield as a copy of enchanted creature";
    }

    public InfiniteReflectionEntersBattlefieldEffect(InfiniteReflectionEntersBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        return permanent != null && permanent.isControlledBy(source.getControllerId())
                && permanent.isCreature(game)
                && !(permanent instanceof PermanentToken);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        MageObject toCopyToObject = ((EntersTheBattlefieldEvent) event).getTarget();
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null && toCopyToObject != null && sourcePermanent.getAttachedTo() != null) {
            Permanent toCopyFromPermanent = game.getPermanent(sourcePermanent.getAttachedTo());
            if (toCopyFromPermanent != null) {
                game.copyPermanent(toCopyFromPermanent, toCopyToObject.getId(), source, new EmptyCopyApplier());
            }
        }
        return false;
    }

    @Override
    public InfiniteReflectionEntersBattlefieldEffect copy() {
        return new InfiniteReflectionEntersBattlefieldEffect(this);
    }

}

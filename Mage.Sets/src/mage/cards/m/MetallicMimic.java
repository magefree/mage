
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.continuous.AddChosenSubtypeEffect;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.enterAttribute.EnterAttributeAddChosenSubtypeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author Styxo
 */
public final class MetallicMimic extends CardImpl {

    public MetallicMimic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // As Metallic Mimic enters the battlefield, choose a creature type.
        AsEntersBattlefieldAbility ability = new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.BoostCreature));
        // Metallic Mimic is the chosen type in addition to its other types.
        ability.addEffect(new EnterAttributeAddChosenSubtypeEffect());
        this.addAbility(ability);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AddChosenSubtypeEffect()));

        // Each other creature you control of the chosen type enters the battlefield with an additional +1/+1 counter on it.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MetallicMimicReplacementEffect()));

    }

    private MetallicMimic(final MetallicMimic card) {
        super(card);
    }

    @Override
    public MetallicMimic copy() {
        return new MetallicMimic(this);
    }

}

class MetallicMimicReplacementEffect extends ReplacementEffectImpl {

    MetallicMimicReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
        staticText = "Each other creature you control of the chosen type enters the battlefield with an additional +1/+1 counter on it";
        setCharacterDefining(true);
    }

    private MetallicMimicReplacementEffect(final MetallicMimicReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Permanent enteringCreature = ((EntersTheBattlefieldEvent) event).getTarget();
        if (enteringCreature != null && sourcePermanent != null
                && enteringCreature.isControlledBy(source.getControllerId())
                && enteringCreature.isCreature(game)
                && !event.getTargetId().equals(source.getSourceId())) {
            SubType subType = ChooseCreatureTypeEffect.getChosenCreatureType(source.getSourceId(), game);
            return subType != null && enteringCreature.hasSubtype(subType, game);
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        if (creature != null) {
            creature.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game, event.getAppliedEffects());
        }
        return false;
    }

    @Override
    public MetallicMimicReplacementEffect copy() {
        return new MetallicMimicReplacementEffect(this);
    }
}

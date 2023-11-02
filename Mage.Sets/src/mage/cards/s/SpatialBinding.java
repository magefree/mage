package mage.cards.s;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SpatialBinding extends CardImpl {

    public SpatialBinding(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}{B}");

        // Pay 1 life: Until your next upkeep, target permanent can't phase out.
        Ability ability = new SimpleActivatedAbility(
                new SpatialBindingReplacementEffect(),
                new PayLifeCost(1)
        );
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
    }

    private SpatialBinding(final SpatialBinding card) {
        super(card);
    }

    @Override
    public SpatialBinding copy() {
        return new SpatialBinding(this);
    }
}

class SpatialBindingReplacementEffect extends ContinuousRuleModifyingEffectImpl {

    public SpatialBindingReplacementEffect() {
        super(Duration.UntilYourNextUpkeepStep, Outcome.Neutral);
        staticText = "until your next upkeep, target permanent can't phase out";
    }

    private SpatialBindingReplacementEffect(final SpatialBindingReplacementEffect effect) {
        super(effect);
    }

    @Override
    public SpatialBindingReplacementEffect copy() {
        return new SpatialBindingReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PHASE_OUT;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        affectedObjectList.clear();
        affectedObjectList.add(new MageObjectReference(source.getFirstTarget(), game));
        affectedObjectsSet = true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        MageObjectReference targetMOR = new MageObjectReference(event.getTargetId(), game);
        return affectedObjectList.stream().anyMatch(mor -> mor.equals(targetMOR));
    }
}
package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.common.DamagedByWatcher;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class BoneShaman extends CardImpl {

    public BoneShaman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {B}: Until end of turn, Bone Shaman gains "Creatures dealt damage by this creature this turn can't be regenerated this turn."
        this.addAbility(new SimpleActivatedAbility(
                new GainAbilitySourceEffect(new SimpleStaticAbility(new BoneShamanEffect()), Duration.EndOfTurn)
                        .setText("Until end of turn, {this} gains \"Creatures dealt damage by this creature" +
                                " this turn can't be regenerated this turn.\""),
                new ManaCostsImpl<>("{B}")
        ), new DamagedByWatcher(false));

    }

    private BoneShaman(final BoneShaman card) {
        super(card);
    }

    @Override
    public BoneShaman copy() {
        return new BoneShaman(this);
    }
}

class BoneShamanEffect extends ContinuousRuleModifyingEffectImpl {

    BoneShamanEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "Creatures dealt damage by this creature this turn can't be regenerated this turn";
    }

    protected BoneShamanEffect(final BoneShamanEffect effect) {
        super(effect);
    }

    @Override
    public BoneShamanEffect copy() {
        return new BoneShamanEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.REGENERATE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        DamagedByWatcher watcher = game.getState().getWatcher(DamagedByWatcher.class, source.getSourceId());
        return watcher != null && watcher.wasDamaged(event.getTargetId(), game);
    }

}

package mage.cards.e;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SourcePhaseOutTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.keyword.PhasingAbility;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ErtaisFamiliar extends CardImpl {

    public ErtaisFamiliar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Phasing
        this.addAbility(PhasingAbility.getInstance());

        // When Ertai's Familiar phases out or leaves the battlefield, mill three cards.
        TriggeredAbility trigger = new OrTriggeredAbility(
                Zone.BATTLEFIELD,
                new MillCardsControllerEffect(3),
                new SourcePhaseOutTriggeredAbility(null, false),
                new LeavesBattlefieldTriggeredAbility(null, false)
        ).setTriggerPhrase("When {this} phases out or leaves the battlefield, ");
        trigger.setWorksPhasedOut(true);
        this.addAbility(trigger);

        // {U}: Until your next upkeep, Ertai's Familiar can't phase out.
        this.addAbility(new SimpleActivatedAbility(
                new ErtaisFamiliarReplacementEffect(),
                new ManaCostsImpl<>("{U}")
        ));
    }

    private ErtaisFamiliar(final ErtaisFamiliar card) {
        super(card);
    }

    @Override
    public ErtaisFamiliar copy() {
        return new ErtaisFamiliar(this);
    }
}

class ErtaisFamiliarReplacementEffect extends ContinuousRuleModifyingEffectImpl {

    public ErtaisFamiliarReplacementEffect() {
        super(Duration.UntilYourNextUpkeepStep, Outcome.Neutral);
        staticText = "until your next upkeep, {this} can't phase out";
    }

    private ErtaisFamiliarReplacementEffect(final ErtaisFamiliarReplacementEffect effect) {
        super(effect);
    }

    @Override
    public ErtaisFamiliarReplacementEffect copy() {
        return new ErtaisFamiliarReplacementEffect(this);
    }


    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PHASE_OUT;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        affectedObjectList.clear();
        affectedObjectList.add(new MageObjectReference(source.getSourceId(), game));
        affectedObjectsSet = true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        MageObjectReference targetMOR = new MageObjectReference(event.getTargetId(), game);
        return affectedObjectList.stream().anyMatch(mor -> mor.equals(targetMOR));
    }
}
package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.HellbentCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class GibberingDescent extends CardImpl {

    public GibberingDescent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{B}{B}");

        // At the beginning of each player's upkeep, that player loses 1 life and discards a card.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                Zone.BATTLEFIELD, new LoseLifeTargetEffect(1).setText("that player loses 1 life"),
                TargetController.ANY, false, true
        );
        ability.addEffect(new DiscardTargetEffect(1).setText("and discards a card"));
        this.addAbility(ability);

        // Hellbent - Skip your upkeep step if you have no cards in hand.
        this.addAbility(new SimpleStaticAbility(new GibberingDescentSkipUpkeepEffect()).setAbilityWord(AbilityWord.HELLBENT));

        // Madness {2}{B}{B}
        this.addAbility(new MadnessAbility(new ManaCostsImpl<>("{2}{B}{B}")));
    }

    private GibberingDescent(final GibberingDescent card) {
        super(card);
    }

    @Override
    public GibberingDescent copy() {
        return new GibberingDescent(this);
    }
}

class GibberingDescentSkipUpkeepEffect extends ContinuousRuleModifyingEffectImpl {

    GibberingDescentSkipUpkeepEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        this.staticText = "skip your upkeep step if you have no cards in hand";
    }

    GibberingDescentSkipUpkeepEffect(final GibberingDescentSkipUpkeepEffect effect) {
        super(effect);
    }

    @Override
    public GibberingDescentSkipUpkeepEffect copy() {
        return new GibberingDescentSkipUpkeepEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.isControlledBy(event.getPlayerId()) && HellbentCondition.instance.apply(game, source);
    }
}

package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.DestroySourceEffect;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.abilities.effects.common.continuous.PlayTheTopCardEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ExperimentalFrenzy extends CardImpl {

    public ExperimentalFrenzy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");

        // You may look at the top card of your library any time.
        this.addAbility(new SimpleStaticAbility(new LookAtTopCardOfLibraryAnyTimeEffect()));

        // You may play lands and cast spells from the top of your library.
        this.addAbility(new SimpleStaticAbility(new PlayTheTopCardEffect()));

        // You can't play cards from your hand.
        this.addAbility(new SimpleStaticAbility(new ExperimentalFrenzyRestrictionEffect()));

        // {3}{R}: Destroy Experimental Frenzy.
        this.addAbility(new SimpleActivatedAbility(new DestroySourceEffect(), new ManaCostsImpl<>("{3}{R}")));
    }

    private ExperimentalFrenzy(final ExperimentalFrenzy card) {
        super(card);
    }

    @Override
    public ExperimentalFrenzy copy() {
        return new ExperimentalFrenzy(this);
    }
}

class ExperimentalFrenzyRestrictionEffect extends ContinuousRuleModifyingEffectImpl {

    ExperimentalFrenzyRestrictionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        this.staticText = "You can't play cards from your hand";
    }

    private ExperimentalFrenzyRestrictionEffect(final ExperimentalFrenzyRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public ExperimentalFrenzyRestrictionEffect copy() {
        return new ExperimentalFrenzyRestrictionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PLAY_LAND
                || event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getPlayerId().equals(source.getControllerId())
                && game.getState().getZone(event.getSourceId()) == Zone.HAND;
    }
}

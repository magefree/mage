package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StealTheShow extends CardImpl {

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARDS_INSTANT_AND_SORCERY);
    private static final Hint hint = new ValueHint("Instant and sorcery cards in your graveyard", xValue);

    public StealTheShow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Choose one or both --
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);

        // * Target player discards any number of cards, then draws that many cards.
        this.getSpellAbility().addEffect(new StealTheShowEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());

        // * Steal the Show deals damage equal to the number of instant and sorcery cards in your graveyard to target creature or planeswalker.
        this.getSpellAbility().addMode(new Mode(new DamageTargetEffect(xValue))
                .addTarget(new TargetCreatureOrPlaneswalker()));
        this.getSpellAbility().addHint(hint);
    }

    private StealTheShow(final StealTheShow card) {
        super(card);
    }

    @Override
    public StealTheShow copy() {
        return new StealTheShow(this);
    }
}

class StealTheShowEffect extends OneShotEffect {

    StealTheShowEffect() {
        super(Outcome.Benefit);
        staticText = "target player discards any number of cards, then draws that many cards";
    }

    private StealTheShowEffect(final StealTheShowEffect effect) {
        super(effect);
    }

    @Override
    public StealTheShowEffect copy() {
        return new StealTheShowEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null) {
            return false;
        }
        int discarded = player.discard(0, Integer.MAX_VALUE, false, source, game).size();
        player.drawCards(discarded, source, game);
        return true;
    }
}

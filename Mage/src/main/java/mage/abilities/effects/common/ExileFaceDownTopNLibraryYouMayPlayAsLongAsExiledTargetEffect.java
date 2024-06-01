package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CastManaAdjustment;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;

/**
 * This exiles the target players' top N cards of library.
 * Each card can be looked at by the source's controller.
 * For each card exiled this way, that player may play|cast that card as long as it stays exiled. (+ mana adjustement)
 * e.g. [[Gonti, Canny Acquisitor]]
 *
 * @author Susucr
 */
public class ExileFaceDownTopNLibraryYouMayPlayAsLongAsExiledTargetEffect extends OneShotEffect {

    private final boolean useCastSpellOnly;
    private final CastManaAdjustment manaAdjustment;
    private final DynamicValue value;

    public ExileFaceDownTopNLibraryYouMayPlayAsLongAsExiledTargetEffect(boolean useCastSpellOnly, CastManaAdjustment manaAdjustment) {
        this(useCastSpellOnly, manaAdjustment, 1);
    }

    public ExileFaceDownTopNLibraryYouMayPlayAsLongAsExiledTargetEffect(boolean useCastSpellOnly, CastManaAdjustment manaAdjustment, int amount) {
        this(useCastSpellOnly, manaAdjustment, StaticValue.get(amount));
    }

    public ExileFaceDownTopNLibraryYouMayPlayAsLongAsExiledTargetEffect(boolean useCastSpellOnly, CastManaAdjustment manaAdjustment, DynamicValue value) {
        super(Outcome.Exile);
        this.value = value;
        this.useCastSpellOnly = useCastSpellOnly;
        this.manaAdjustment = manaAdjustment;
    }

    private ExileFaceDownTopNLibraryYouMayPlayAsLongAsExiledTargetEffect(final ExileFaceDownTopNLibraryYouMayPlayAsLongAsExiledTargetEffect effect) {
        super(effect);
        this.value = effect.value;
        this.useCastSpellOnly = effect.useCastSpellOnly;
        this.manaAdjustment = effect.manaAdjustment;
    }

    @Override
    public ExileFaceDownTopNLibraryYouMayPlayAsLongAsExiledTargetEffect copy() {
        return new ExileFaceDownTopNLibraryYouMayPlayAsLongAsExiledTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null) {
            return false;
        }

        int amount = value.calculate(game, source, this);
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, amount));
        if (cards.isEmpty()) {
            return false;
        }

        return new ExileFaceDownYouMayPlayAsLongAsExiledTargetEffect(useCastSpellOnly, manaAdjustment)
                .setTargetPointer(new FixedTargets(cards, game))
                .apply(game, source);
    }

}
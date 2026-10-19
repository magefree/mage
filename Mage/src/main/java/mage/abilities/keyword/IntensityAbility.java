package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.StaticAbility;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.hint.Hint;
import mage.cards.Card;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

/** Intensity belongs to a card and survives zone changes. It is not a counter. */
public class IntensityAbility extends StaticAbility {

    private final int startingIntensity;

    public IntensityAbility(int startingIntensity) {
        super(Zone.ALL, new InfoEffect("Starting intensity " + startingIntensity));
        this.startingIntensity = startingIntensity;
        addHint(IntensityHint.instance);
    }

    private IntensityAbility(final IntensityAbility ability) {
        super(ability);
        this.startingIntensity = ability.startingIntensity;
    }

    @Override
    public IntensityAbility copy() {
        return new IntensityAbility(this);
    }

    public static int getIntensity(Card card, Game game) {
        Integer intensity = (Integer) game.getState().getValue(key(card));
        Permanent permanent = game.getPermanent(card.getId());
        return intensity != null ? intensity : (permanent == null ? card : permanent).getAbilities(game).stream()
                .filter(IntensityAbility.class::isInstance).map(IntensityAbility.class::cast)
                .mapToInt(ability -> ability.startingIntensity).findFirst().orElse(0);
    }

    public static void intensify(Card card, int amount, Game game) {
        game.getState().setValue(key(card), getIntensity(card, game) + amount);
    }

    private static String key(Card card) {
        Card main = card.getMainCard();
        return "intensity_" + (main == null ? card.getId() : main.getId());
    }
}

enum IntensityHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        Card card = game.getPermanent(ability.getSourceId());
        if (card == null) {
            card = game.getCard(ability.getSourceId());
        }
        return "Intensity: " + (card == null ? 0 : IntensityAbility.getIntensity(card, game));
    }

    @Override
    public Hint copy() {
        return this;
    }
}

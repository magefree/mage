package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.keyword.MorphAbility;
import mage.cards.Card;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.game.Game;

import java.util.UUID;

/**
 * @author LevelX2
 */

public class CastAsThoughItHadFlashAllEffect extends AsThoughEffectImpl {

    private final FilterCard filter;
    private final boolean anyPlayer;

    public CastAsThoughItHadFlashAllEffect(Duration duration, FilterCard filter) {
        this(duration, filter, false);
    }

    public CastAsThoughItHadFlashAllEffect(Duration duration, FilterCard filter, boolean anyPlayer) {
        super(AsThoughEffectType.CAST_AS_INSTANT, duration, Outcome.Benefit);
        this.filter = filter;
        this.anyPlayer = anyPlayer;
        staticText = setText();
    }

    public CastAsThoughItHadFlashAllEffect(final CastAsThoughItHadFlashAllEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.anyPlayer = effect.anyPlayer;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public CastAsThoughItHadFlashAllEffect copy() {
        return new CastAsThoughItHadFlashAllEffect(this);
    }

    @Override
    public boolean applies(UUID affectedSpellId, Ability source, UUID affectedControllerId, Game game) {
        if (anyPlayer || source.isControlledBy(affectedControllerId)) {
            Card card = game.getCard(affectedSpellId);
            if (card != null) {
                //Allow lands with morph to be played at instant speed
                if (card.isLand(game)) {
                    boolean morphAbility = card.getAbilities().stream().anyMatch(MorphAbility.class::isInstance);
                    if (morphAbility) {
                        Card cardCopy = card.copy();
                        cardCopy.removeAllCardTypes(game);
                        cardCopy.addCardType(game, CardType.CREATURE);
                        return filter.match(cardCopy, affectedControllerId, source, game);
                    }
                }
                return filter.match(card, affectedControllerId, source, game);
            }
        }
        return false;
    }

    private String setText() {
        StringBuilder sb = new StringBuilder();
        if (anyPlayer) {
            sb.append("Any player");
        } else {
            sb.append("You");
        }
        sb.append(" may cast ");
        sb.append(filter.getMessage());
        if (!duration.toString().isEmpty()) {
            if (duration == Duration.EndOfTurn) {
                sb.append(" this turn");
            } else {
                sb.append(' ');
                sb.append(' ');
                sb.append(duration.toString());
            }
        }
        return sb.append(" as though they had flash").toString();
    }
}

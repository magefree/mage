package mage.cards.l;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.players.Player;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class LucidDreams extends CardImpl {

    public LucidDreams(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}{U}");

        // Draw X cards, where X is the number of card types among cards in your graveyard.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(LucidDreamsValue.instance)
                .setText("draw X cards, where X is the number of card types among cards in your graveyard"));
        this.getSpellAbility().addHint(CardTypesInGraveyardHint.YOU);
    }

    private LucidDreams(final LucidDreams card) {
        super(card);
    }

    @Override
    public LucidDreams copy() {
        return new LucidDreams(this);
    }
}

enum LucidDreamsValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player player = game.getPlayer(sourceAbility.getControllerId());
        return player == null ? 0 : player.getGraveyard()
                .getCards(game)
                .stream()
                .map(MageObject::getCardType)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet())
                .size();
    }

    @Override
    public LucidDreamsValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "";
    }
}

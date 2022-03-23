package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.players.PlayerList;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Kindle extends CardImpl {

    private static final FilterCard filter = new FilterCard("2 plus the number of cards named Kindle");

    static {
        filter.add(new NamePredicate("Kindle"));
    }

    public Kindle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");


        // Kindle deals X damage to any target, where X is 2 plus the number of cards named Kindle in all graveyards.
        Effect effect = new DamageTargetEffect(new KindleCardsInAllGraveyardsCount(filter));
        effect.setText("{this} deals X damage to any target, where X is 2 plus the number of cards named {this} in all graveyards");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private Kindle(final Kindle card) {
        super(card);
    }

    @Override
    public Kindle copy() {
        return new Kindle(this);
    }
}

class KindleCardsInAllGraveyardsCount implements DynamicValue {

    private final FilterCard filter;

    public KindleCardsInAllGraveyardsCount(FilterCard filter) {
        this.filter = filter;
    }

    private KindleCardsInAllGraveyardsCount(KindleCardsInAllGraveyardsCount dynamicValue) {
        this.filter = dynamicValue.filter;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int amount = 0;
        PlayerList playerList = game.getPlayerList().copy();
        for (UUID playerUUID : playerList) {
            Player player = game.getPlayer(playerUUID);
            if (player != null) {
                amount += player.getGraveyard().count(filter, sourceAbility.getControllerId(), sourceAbility, game);
            }
        }
        return amount + 2;
    }

    @Override
    public KindleCardsInAllGraveyardsCount copy() {
        return new KindleCardsInAllGraveyardsCount(this);
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return filter.getMessage() + " in all graveyards";
    }
}

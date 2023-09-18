package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class TrazynTheInfinite extends CardImpl {

    public TrazynTheInfinite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.NECRON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Prismatic Gallery -- As long as Trazyn the Infinite is on the battlefield, it has all activated abilities of all artifact cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(new TrazynTheInfiniteEffect()).withFlavorWord("Prismatic Gallery"));
    }

    private TrazynTheInfinite(final TrazynTheInfinite card) {
        super(card);
    }

    @Override
    public TrazynTheInfinite copy() {
        return new TrazynTheInfinite(this);
    }
}

class TrazynTheInfiniteEffect extends ContinuousEffectImpl {

    TrazynTheInfiniteEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "as long as {this} is on the battlefield, " +
                "it has all activated abilities of all artifact cards in your graveyard";
        this.dependendToTypes.add(DependencyType.AddingAbility); // Yixlid Jailer
    }

    private TrazynTheInfiniteEffect(final TrazynTheInfiniteEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        Player player = game.getPlayer(source.getControllerId());
        if (permanent == null || player == null) {
            return false;
        }
        Set<Ability> abilities = player.getGraveyard()
                .getCards(StaticFilters.FILTER_CARD_ARTIFACT, game)
                .stream()
                .map(card -> card.getAbilities(game))
                .flatMap(Collection::stream)
                .filter(ActivatedAbility.class::isInstance)
                .collect(Collectors.toSet());
        for (Ability ability : abilities) {
            permanent.addAbility(ability, source.getSourceId(), game);
        }
        return true;
    }

    @Override
    public TrazynTheInfiniteEffect copy() {
        return new TrazynTheInfiniteEffect(this);
    }
}

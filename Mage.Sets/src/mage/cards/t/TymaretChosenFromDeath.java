package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SetBaseToughnessSourceEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class TymaretChosenFromDeath extends CardImpl {

    public TymaretChosenFromDeath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{B}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMIGOD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(0);

        // Tymaret's toughness is equal to your devotion to black.
        this.addAbility(new SimpleStaticAbility(
                        Zone.ALL, new SetBaseToughnessSourceEffect(DevotionCount.B, Duration.EndOfGame)
                        .setText("{this}'s toughness is equal to your devotion to black")
                ).addHint(DevotionCount.B.getHint())
        );

        // {1}{B}: Exile up to two target cards from graveyards. You gain 1 life for each creature card exiled this way.
        Ability ability = new SimpleActivatedAbility(new TymaretChosenFromDeathEffect(), new ManaCostsImpl<>("{1}{B}"));
        ability.addTarget(new TargetCardInGraveyard(0, 2, StaticFilters.FILTER_CARD));
        this.addAbility(ability);
    }

    private TymaretChosenFromDeath(final TymaretChosenFromDeath card) {
        super(card);
    }

    @Override
    public TymaretChosenFromDeath copy() {
        return new TymaretChosenFromDeath(this);
    }
}

class TymaretChosenFromDeathEffect extends OneShotEffect {

    TymaretChosenFromDeathEffect() {
        super(Outcome.Benefit);
        staticText = "exile up to two target cards from graveyards. " +
                "You gain 1 life for each creature card exiled this way";
    }

    private TymaretChosenFromDeathEffect(final TymaretChosenFromDeathEffect effect) {
        super(effect);
    }

    @Override
    public TymaretChosenFromDeathEffect copy() {
        return new TymaretChosenFromDeathEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(
                source.getTargets()
                        .stream()
                        .map(Target::getTargets)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toSet())
        );
        player.moveCards(cards, Zone.EXILED, source, game);
        int lifeGain = cards
                .getCards(game)
                .stream()
                .filter(card -> card.isCreature(game))
                .map(Card::getId)
                .map(game.getState()::getZone)
                .filter(Zone.EXILED::equals)
                .mapToInt(x -> 1)
                .sum();
        player.gainLife(lifeGain, game, source);
        return true;
    }
}
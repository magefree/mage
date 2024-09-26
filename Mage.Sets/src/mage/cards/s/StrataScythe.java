package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

import java.util.Set;
import java.util.UUID;

/**
 * @author Loki
 */
public final class StrataScythe extends CardImpl {

    public StrataScythe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.subtype.add(SubType.EQUIPMENT);

        // Imprint — When Strata Scythe enters the battlefield, search your library for a land card, exile it, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new StrataScytheImprintEffect()).setAbilityWord(AbilityWord.IMPRINT));

        // Equipped creature gets +1/+1 for each land on the battlefield with the same name as the exiled card.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(
                StrataScytheValue.instance, StrataScytheValue.instance
        )));

        // Equip {3}
        this.addAbility(new EquipAbility(3, false));
    }

    private StrataScythe(final StrataScythe card) {
        super(card);
    }

    @Override
    public StrataScythe copy() {
        return new StrataScythe(this);
    }

}

class StrataScytheImprintEffect extends OneShotEffect {

    StrataScytheImprintEffect() {
        super(Outcome.Exile);
        staticText = "search your library for a land card, exile it, then shuffle";
    }

    private StrataScytheImprintEffect(final StrataScytheImprintEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary(StaticFilters.FILTER_CARD_LAND_A);
        player.searchLibrary(target, source, game);
        Card card = player.getLibrary().getCard(target.getFirstTarget(), game);
        if (card != null) {
            player.moveCardsToExile(
                    card, source, game, true,
                    CardUtil.getExileZoneId(game, source),
                    CardUtil.getSourceName(game, source)
            );
        }
        player.shuffleLibrary(source, game);
        return true;
    }

    @Override
    public StrataScytheImprintEffect copy() {
        return new StrataScytheImprintEffect(this);
    }

}

enum StrataScytheValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability source, Effect effect) {
        ExileZone exileZone = game
                .getExile()
                .getExileZone(CardUtil.getExileZoneId(
                        game, source.getSourceId(),
                        game.getState().getZoneChangeCounter(source.getSourceId())
                ));
        if (exileZone == null || exileZone.isEmpty()) {
            return 0;
        }
        Set<Card> cards = exileZone.getCards(game);
        return game
                .getBattlefield()
                .getActivePermanents(StaticFilters.FILTER_LAND, source.getControllerId(), source, game)
                .stream()
                .filter(permanent -> cards.stream().anyMatch(card -> card.sharesName(permanent, game)))
                .mapToInt(x -> 1)
                .sum();
    }

    @Override
    public StrataScytheValue copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "land on the battlefield with the same name as the exiled card";
    }
}

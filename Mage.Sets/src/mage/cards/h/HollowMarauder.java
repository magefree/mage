package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HollowMarauder extends CardImpl {

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURE, 1);
    private static final Hint hint = new ValueHint("Creatures in your graveyard", xValue);

    public HollowMarauder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{B}");

        this.subtype.add(SubType.SPECTER);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // This spell costs {1} less to cast for each creature card in your graveyard.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionForEachSourceEffect(1, xValue)
        ).addHint(hint));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Hollow Marauder enters the battlefield, any number of target opponents each discard a card. For each of those opponents who didn't discard a card with mana value 4 or greater, draw a card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new HollowMarauderEffect());
        ability.addTarget(new TargetOpponent(0, Integer.MAX_VALUE, false));
        this.addAbility(ability);
    }

    private HollowMarauder(final HollowMarauder card) {
        super(card);
    }

    @Override
    public HollowMarauder copy() {
        return new HollowMarauder(this);
    }
}

class HollowMarauderEffect extends OneShotEffect {

    HollowMarauderEffect() {
        super(Outcome.Benefit);
        staticText = "any number of target opponents each discard a card. For each of those opponents " +
                "who didn't discard a card with mana value 4 or greater, draw a card";
    }

    private HollowMarauderEffect(final HollowMarauderEffect effect) {
        super(effect);
    }

    @Override
    public HollowMarauderEffect copy() {
        return new HollowMarauderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int count = 0;
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            Player opponent = game.getPlayer(source.getControllerId());
            if (opponent == null) {
                continue;
            }
            Card card = opponent.discard(1, false, false, source, game).getRandom(game);
            if (card != null && card.getManaValue() >= 4) {
                count++;
            }
        }
        if (count < 1) {
            return true;
        }
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.drawCards(count, source, game);
        }
        return true;
    }
}

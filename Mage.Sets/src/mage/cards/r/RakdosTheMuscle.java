package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.util.CardUtil;

import java.util.Set;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class RakdosTheMuscle extends CardImpl {

    public RakdosTheMuscle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMON);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever you sacrifice another creature, exile cards equal to its mana value from the top of target player's library. Until your next end step, you may play those cards, and mana of any type can be spent to cast those spells.
        Ability ability = new SacrificePermanentTriggeredAbility(Zone.BATTLEFIELD,
                new RakdosTheMuscleEffect(),
                StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE,
                TargetController.YOU, SetTargetPointer.PERMANENT, false
        );
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // Sacrifice another creature: Rakdos, the Muscle gains indestructible until end of turn. Tap it. Activate only once each turn.
        ability = new LimitedTimesPerTurnActivatedAbility(
                Zone.BATTLEFIELD,
                new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn),
                new SacrificeTargetCost(StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL)
        );
        ability.addEffect(new TapSourceEffect().setText("tap it"));
        this.addAbility(ability);
    }

    private RakdosTheMuscle(final RakdosTheMuscle card) {
        super(card);
    }

    @Override
    public RakdosTheMuscle copy() {
        return new RakdosTheMuscle(this);
    }
}

class RakdosTheMuscleEffect extends OneShotEffect {

    RakdosTheMuscleEffect() {
        super(Outcome.Benefit);
        staticText = "exile cards equal to its mana value from the top of target player's library. "
                + "Until your next end step, you may play those cards, and mana of any type can be spent to cast those spells";
    }

    private RakdosTheMuscleEffect(final RakdosTheMuscleEffect effect) {
        super(effect);
    }

    @Override
    public RakdosTheMuscleEffect copy() {
        return new RakdosTheMuscleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(source.getFirstTarget());
        Permanent sacrificed = game.getPermanentOrLKIBattlefield(getTargetPointer().getFirst(game, source));
        if (player == null || controller == null || sacrificed == null) {
            return false;
        }
        int amount = sacrificed.getManaValue();
        if (amount <= 0) {
            return false;
        }

        Set<Card> cards = player.getLibrary().getTopCards(game, amount);
        if (cards.isEmpty()) {
            return false;
        }
        player.moveCardsToExile(cards, source, game, true, CardUtil.getExileZoneId(game, source), CardUtil.getSourceName(game, source));
        // remove cards that could not be moved to exile
        cards.removeIf(card -> !Zone.EXILED.equals(game.getState().getZone(card.getId())));
        for (Card card : cards) {
            CardUtil.makeCardPlayable(game, source, card, false, Duration.UntilYourNextEndStep, true, controller.getId(), null);
        }
        return true;
    }

}
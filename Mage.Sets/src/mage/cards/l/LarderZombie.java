package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DefenderAbility;
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
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LarderZombie extends CardImpl {

    public LarderZombie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Tap three untapped creatures you control: Look at the top card of your library. You may put it into your graveyard.
        this.addAbility(new SimpleActivatedAbility(
                new LarderZombieEffect(),
                new TapTargetCost(new TargetControlledPermanent(
                        3, StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURES
                ))
        ));
    }

    private LarderZombie(final LarderZombie card) {
        super(card);
    }

    @Override
    public LarderZombie copy() {
        return new LarderZombie(this);
    }
}

class LarderZombieEffect extends OneShotEffect {

    LarderZombieEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top card of your library. You may put it into your graveyard";
    }

    private LarderZombieEffect(final LarderZombieEffect effect) {
        super(effect);
    }

    @Override
    public LarderZombieEffect copy() {
        return new LarderZombieEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        player.lookAtCards("Top card of your library", card, game);
        if (player.chooseUse(Outcome.AIDontUseIt, "Put the top card of your library into your graveyard?", source, game)) {
            player.moveCards(card, Zone.GRAVEYARD, source, game);
        }
        return true;
    }
}

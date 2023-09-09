package mage.cards.a;

import java.util.HashSet;
import java.util.Set;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.OpponentsCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;
import mage.cards.Card;

/**
 * @author JayDi85
 */
public final class AvatarOfGrowth extends CardImpl {

    public AvatarOfGrowth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // This spell costs {1} less to cast for each opponent you have.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SpellCostReductionForEachSourceEffect(1, OpponentsCount.instance)
                .setText("This spell costs {1} less to cast for each opponent you have")));

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Avatar of Growth enters the battlefield, each player searches their library for up to two basic land cards, puts them onto the battlefield, then shuffles their library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AvatarOfGrowthSearchEffect()));
    }

    private AvatarOfGrowth(final AvatarOfGrowth card) {
        super(card);
    }

    @Override
    public AvatarOfGrowth copy() {
        return new AvatarOfGrowth(this);
    }
}

class AvatarOfGrowthSearchEffect extends OneShotEffect {

    public AvatarOfGrowthSearchEffect() {
        super(Outcome.Detriment);
        this.staticText = "each player searches their library for up to two basic land cards, puts them onto the battlefield, then shuffles";
    }

    private AvatarOfGrowthSearchEffect(final AvatarOfGrowthSearchEffect effect) {
        super(effect);
    }

    @Override
    public AvatarOfGrowthSearchEffect copy() {
        return new AvatarOfGrowthSearchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Set<Card> toBattlefield = new HashSet<>();
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    TargetCardInLibrary target = new TargetCardInLibrary(0, 2, StaticFilters.FILTER_CARD_BASIC_LAND);
                    if (player.searchLibrary(target, source, game)) {
                        if (!target.getTargets().isEmpty()) {
                            toBattlefield.addAll(new CardsImpl(target.getTargets()).getCards(game));
                        }
                    }
                }
            }
            // must happen simultaneously Rule 101.4
            controller.moveCards(toBattlefield, Zone.BATTLEFIELD, source, game, false, false, true, null);

            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    player.shuffleLibrary(source, game);
                }
            }
            return true;
        }
        return false;
    }
}

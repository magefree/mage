package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.abilities.effects.common.continuous.PlayTheTopCardEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;

import java.util.Comparator;
import java.util.UUID;

import static mage.filter.StaticFilters.FILTER_CARD_LAND;
import static mage.filter.StaticFilters.FILTER_LAND;

/**
 *
 * @author htrajan
 */
public final class VergeRangers extends CardImpl {

    public VergeRangers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // You may look at the top card of your library any time.
        this.addAbility(new SimpleStaticAbility(new LookAtTopCardOfLibraryAnyTimeEffect()));

        // As long as an opponent controls more lands than you, you may play lands from the top of your library.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new VergeRangersEffect()));
    }

    private VergeRangers(final VergeRangers card) {
        super(card);
    }

    @Override
    public VergeRangers copy() {
        return new VergeRangers(this);
    }
}

class VergeRangersEffect extends PlayTheTopCardEffect {

    public VergeRangersEffect() {
        super(FILTER_CARD_LAND);
        staticText = "As long as an opponent controls more lands than you, you may play lands from the top of your library.";
    }

    public VergeRangersEffect(final VergeRangersEffect effect) {
        super(effect);
    }

    @Override
    public VergeRangersEffect copy() {
        return new VergeRangersEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability affectedAbility, Ability source, Game game, UUID playerId) {
        if (super.applies(objectId, affectedAbility, source, game, playerId)) {
            int myLandCount = game.getBattlefield().countAll(FILTER_LAND, playerId, game);
            int maxOpponentLandCount = game.getOpponents(playerId).stream()
                    .map(opponentId -> game.getBattlefield().countAll(FILTER_LAND, opponentId, game))
                    .max(Comparator.naturalOrder())
                    .orElse(0);
            return maxOpponentLandCount > myLandCount;
        }
        return false;
    }
}
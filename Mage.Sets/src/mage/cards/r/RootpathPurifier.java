package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.*;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author @stwalsh4118
 */
public final class RootpathPurifier extends CardImpl {

    public RootpathPurifier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Lands you control and land cards in your library are basic.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new LandsControlledAndInLibraryAreBasicEffect()));

    }

    private RootpathPurifier(final RootpathPurifier card) {
        super(card);
    }

    @Override
    public RootpathPurifier copy() {
        return new RootpathPurifier(this);
    }
}

class LandsControlledAndInLibraryAreBasicEffect extends ContinuousEffectImpl {

    public LandsControlledAndInLibraryAreBasicEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Neutral);
        staticText = "Lands you control and land cards in your library are basic.";
        this.dependencyTypes.add(DependencyType.BecomeBasicLand);

    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {

            // permaments
            for (Permanent perm : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND, source.getControllerId(), game)) {

                perm.addSuperType(SuperType.BASIC);
            }

            List<Card> affectedCards = new ArrayList<>();


            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player == null) {
                    continue;
                }
                // library
                affectedCards.addAll(player.getLibrary().getCards(game));
            }


            // give all lands in library basic supertype
            affectedCards.forEach(card -> {
                if(card.getCardType().contains(CardType.LAND)) {
                    game.getState().getCreateMageObjectAttribute(card, game).getSuperType().add(SuperType.BASIC);
                }
            });
            return true;
        }
        return false;
    }
    @Override
    public LandsControlledAndInLibraryAreBasicEffect copy() {
        return new LandsControlledAndInLibraryAreBasicEffect(this);
    }

    private LandsControlledAndInLibraryAreBasicEffect(LandsControlledAndInLibraryAreBasicEffect effect) {
        super(effect);
    }
}

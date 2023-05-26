package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RootpathPurifier extends CardImpl {

    public RootpathPurifier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Lands you control and land cards in your library are basic.
        this.addAbility(new SimpleStaticAbility(new RootpathPurifierEffect()));
    }

    private RootpathPurifier(final RootpathPurifier card) {
        super(card);
    }

    @Override
    public RootpathPurifier copy() {
        return new RootpathPurifier(this);
    }
}

class RootpathPurifierEffect extends ContinuousEffectImpl {

    RootpathPurifierEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        staticText = "lands you control and land cards in your library are basic";
    }

    private RootpathPurifierEffect(final RootpathPurifierEffect effect) {
        super(effect);
    }

    @Override
    public RootpathPurifierEffect copy() {
        return new RootpathPurifierEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND,
                source.getControllerId(), source, game
        )) {
            permanent.addSuperType(game, SuperType.BASIC);
        }
        for (Card card : player.getLibrary().getCards(game)) {
            if (card.isLand(game)) {
                card.addSuperType(game, SuperType.BASIC);
            }
        }
        return true;
    }
}

package mage.cards.h;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author jeffwadsworth & L_J
 */
public final class HellcarverDemon extends CardImpl {

    public HellcarverDemon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}{B}");
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        this.addAbility(FlyingAbility.getInstance());

        // Whenever Hellcarver Demon deals combat damage to a player, sacrifice all other permanents you 
        // control and discard your hand. Exile the top six cards of your library. You may cast any number 
        // of nonland cards exiled this way without paying their mana costs.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new HellcarverDemonEffect(), false));
    }

    private HellcarverDemon(final HellcarverDemon card) {
        super(card);
    }

    @Override
    public HellcarverDemon copy() {
        return new HellcarverDemon(this);
    }
}

class HellcarverDemonEffect extends OneShotEffect {

    public HellcarverDemonEffect() {
        super(Outcome.PlayForFree);
        staticText = "sacrifice all other permanents you control and discard your hand. "
                + "Exile the top six cards of your library. You may cast any number of "
                + "spells from among cards exiled this way without paying their mana costs";
    }

    public HellcarverDemonEffect(final HellcarverDemonEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        MageObjectReference sourceMor = new MageObjectReference(source);
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_PERMANENT,
                source.getControllerId(), source.getSourceId(), game
        )) {
            if (!sourceMor.refersTo(permanent, game)) {
                permanent.sacrifice(source, game);
            }
        }
        controller.discard(controller.getHand(), false, source, game);
        CardUtil.castMultipleWithAttributeForFree(
                controller, source, game, new CardsImpl(
                        controller.getLibrary().getTopCards(game, 6)
                ), StaticFilters.FILTER_CARD
        );
        return true;
    }

    @Override
    public HellcarverDemonEffect copy() {
        return new HellcarverDemonEffect(this);
    }
}

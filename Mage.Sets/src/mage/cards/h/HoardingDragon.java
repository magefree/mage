package mage.cards.h;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromExileEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class HoardingDragon extends CardImpl {

    public HoardingDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(FlyingAbility.getInstance());

        // When Hoarding Dragon enters the battlefield, you may search your library for an artifact card, exile it, then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new HoardingDragonEffect(), true));

        // When Hoarding Dragon dies, you may put the exiled card into its owner's hand.
        this.addAbility(new DiesSourceTriggeredAbility(new ReturnFromExileEffect(Zone.HAND), false));
    }

    private HoardingDragon(final HoardingDragon card) {
        super(card);
    }

    @Override
    public HoardingDragon copy() {
        return new HoardingDragon(this);
    }

}

class HoardingDragonEffect extends OneShotEffect {

    HoardingDragonEffect() {
        super(Outcome.Exile);
        this.staticText = "you may search your library for an artifact card, exile it, then shuffle your library";
    }

    private HoardingDragonEffect(final HoardingDragonEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        UUID exileId = CardUtil.getExileZoneId(game, source);
        if (controller == null || sourceObject == null) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary(new FilterArtifactCard());
        controller.searchLibrary(target, source, game);
        Card card = controller.getLibrary().getCard(target.getFirstTarget(), game);
        if (card != null) {
            controller.moveCardToExileWithInfo(card, exileId, sourceObject.getIdName(), source, game, Zone.LIBRARY, true);
        }
        controller.shuffleLibrary(source, game);
        return true;
    }

    @Override
    public HoardingDragonEffect copy() {
        return new HoardingDragonEffect(this);
    }
}

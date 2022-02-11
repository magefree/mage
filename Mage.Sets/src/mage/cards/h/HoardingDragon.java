package mage.cards.h;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
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
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.ReturnFromExileForSourceEffect;
import mage.target.targetpointer.FixedTarget;

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
        this.addAbility(new DiesSourceTriggeredAbility(new ReturnFromExileForSourceEffect(Zone.HAND), true));
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
        this.staticText = "search your library for an artifact card, exile it, then shuffle";
    }

    private HoardingDragonEffect(final HoardingDragonEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || sourceObject == null) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary(StaticFilters.FILTER_CARD_ARTIFACT);
        controller.searchLibrary(target, source, game);
        Card card = controller.getLibrary().getCard(target.getFirstTarget(), game);
        if (card != null) {
            ExileTargetForSourceEffect effect = new ExileTargetForSourceEffect();
            effect.setTargetPointer(new FixedTarget(target.getFirstTarget()));
            effect.apply(game, source);
        }
        controller.shuffleLibrary(source, game);
        return true;
    }

    @Override
    public HoardingDragonEffect copy() {
        return new HoardingDragonEffect(this);
    }
}

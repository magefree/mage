package mage.cards.x;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.token.XenagosSatyrToken;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class XenagosTheReveler extends CardImpl {

    public XenagosTheReveler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{R}{G}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.XENAGOS);

        this.setStartingLoyalty(3);

        // +1: Add X mana in any combination of {R} and/or {G}, where X is the number of creatures you control.
        this.addAbility(new LoyaltyAbility(new XenagosManaEffect(), +1)
                .addHint(CreaturesYouControlHint.instance));

        // 0: Create a 2/2 red and green Satyr creature token with haste.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new XenagosSatyrToken()), 0));

        // -6: Exile the top seven cards of your library. You may put any number of creature and/or land cards from among them onto the battlefield.
        this.addAbility(new LoyaltyAbility(new XenagosExileEffect(), -6));

    }

    private XenagosTheReveler(final XenagosTheReveler card) {
        super(card);
    }

    @Override
    public XenagosTheReveler copy() {
        return new XenagosTheReveler(this);
    }
}

class XenagosManaEffect extends OneShotEffect {

    public XenagosManaEffect() {
        super(Outcome.PutManaInPool);
        this.staticText = "Add X mana in any combination of {R} and/or {G}, where X is the number of creatures you control";
    }

    public XenagosManaEffect(final XenagosManaEffect effect) {
        super(effect);
    }

    @Override
    public XenagosManaEffect copy() {
        return new XenagosManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            int x = game.getBattlefield().count(new FilterControlledCreaturePermanent(), source.getControllerId(), source, game);
            if (x == 0) {
                return false;
            }

            Mana mana = new Mana();
            int redCount = player.getAmount(0, x, "How much <b>RED</b> mana add to pool? (available: " + x + ", another mana goes to <b>GREEN</b>)?", game);
            int greenCount = Math.max(0, x - redCount);
            mana.setRed(redCount);
            mana.setGreen(greenCount);
            if (mana.count() > 0) {
                player.getManaPool().addMana(mana, game, source);
                return true;
            }
        }
        return false;
    }
}

class XenagosExileEffect extends OneShotEffect {

    public XenagosExileEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Exile the top seven cards of your library. You may put any number of creature and/or land cards from among them onto the battlefield";
    }

    public XenagosExileEffect(final XenagosExileEffect effect) {
        super(effect);
    }

    @Override
    public XenagosExileEffect copy() {
        return new XenagosExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards exiledCards = new CardsImpl();
            exiledCards.addAll(controller.getLibrary().getTopCards(game, 7));
            controller.moveCards(exiledCards, Zone.EXILED, source, game);
            FilterCard filter = new FilterCard("creature and/or land cards to put onto the battlefield");
            filter.add(Predicates.or(CardType.CREATURE.getPredicate(),
                    CardType.LAND.getPredicate()));
            TargetCard target1 = new TargetCard(0, Integer.MAX_VALUE, Zone.EXILED, filter);
            target1.setNotTarget(true);
            if (!exiledCards.isEmpty()
                    && target1.canChoose(source.getControllerId(), source, game)
                    && controller.choose(Outcome.PutCardInPlay, exiledCards, target1, game)) {
                controller.moveCards(new CardsImpl(target1.getTargets()), Zone.BATTLEFIELD, source, game);
            }
            return true;
        }
        return false;
    }
}

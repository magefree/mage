package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author bobby-mccann
 */
public final class RadagastTheBrown extends CardImpl {

    static final FilterPermanent filter = new FilterControlledCreaturePermanent("{this} or another nontoken creature you control");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public RadagastTheBrown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Whenever Radagast the Brown or another nontoken creature enters the battlefield under your control,
        // look at the top X cards of your library, where X is that creature's mana value.
        // You may reveal a creature card from among them that doesn't share a creature type with a creature you control
        // and put it into your hand.
        // Put the rest on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD,
                new RadagastEffect(),
                filter, false
        ));
    }

    private RadagastTheBrown(final RadagastTheBrown card) {
        super(card);
    }

    @Override
    public RadagastTheBrown copy() {
        return new RadagastTheBrown(this);
    }
}

class RadagastEffect extends OneShotEffect {

    static final FilterCreatureCard filter = new FilterCreatureCard("creature card that doesn't share a creature type with a creature you control");

    static {
        filter.add(SharesACreatureTypeWithCreaturesYouControl.FALSE);
    }

    public RadagastEffect() {
        super(Outcome.Benefit);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enteringBattlefield = (Permanent) getValue("permanentEnteringBattlefield");
        if (enteringBattlefield == null) {
            return false;
        }
        int manaValue = enteringBattlefield.getManaValue();
        if (manaValue == 0) {
            return false;
        }
        new LookLibraryAndPickControllerEffect(
                manaValue, 1,
                filter,
                PutCards.HAND, PutCards.BOTTOM_RANDOM
        );
        return false;
    }

    @Override
    public Effect copy() {
        return null;
    }
}

enum SharesACreatureTypeWithCreaturesYouControl implements Predicate<Card> {
    TRUE(true),
    FALSE(false);
    private final boolean doesShare;

    SharesACreatureTypeWithCreaturesYouControl(boolean doesShare) {
        this.doesShare = doesShare;
    }
    @Override
    public boolean apply(Card card, Game game) {
        UUID playerId = card.getOwnerId();
        List<Permanent> creaturesYouControl = game.getBattlefield().getActivePermanents(
                new FilterControlledCreaturePermanent(),
                playerId,
                game
        );
        for (Permanent creature : creaturesYouControl) {
            creature.shareCreatureTypes(game, card);
            return doesShare;
        }
        return !doesShare;
    }
}
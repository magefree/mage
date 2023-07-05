package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
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

    static final FilterPermanent etbFilter = new FilterControlledCreaturePermanent("{this} or another nontoken creature you control");

    static {
        etbFilter.add(TokenPredicate.FALSE);
    }

    static final FilterCard cardFilter = new FilterCard("creature card that doesn't share a creature type with a creature you control");

    static {
        cardFilter.add(CardType.CREATURE.getPredicate());
        cardFilter.add(SharesACreatureTypeWithCreaturesYouControl.DOES_NOT);
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
                new LookLibraryAndPickControllerEffect(
                        new TargetManaValue(), 1,
                        cardFilter,
                        PutCards.HAND, PutCards.BOTTOM_RANDOM
                ),
                etbFilter, false
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

class TargetManaValue implements DynamicValue {

    @Override
    public int calculate(Game game, Ability source, Effect effect) {
        if (!(source instanceof EntersBattlefieldAllTriggeredAbility)) {
            return 0;
        }
        UUID enteredId = ((EntersBattlefieldControlledTriggeredAbility) source).getTriggerEvent().getTargetId();
        Permanent enteredBattlefield = game.getPermanent(enteredId);
        if (enteredBattlefield == null) {
            return 0;
        }
        return enteredBattlefield.getManaValue();
    }

    @Override
    public DynamicValue copy() {
        return new TargetManaValue();
    }

    @Override
    public String getMessage() {
        return "that creature's mana value";
    }
}

enum SharesACreatureTypeWithCreaturesYouControl implements Predicate<Card> {
    DOES(true),
    DOES_NOT(false);
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
            if (creature.shareCreatureTypes(game, card)) {
                return doesShare;
            }
        }
        return !doesShare;
    }
}
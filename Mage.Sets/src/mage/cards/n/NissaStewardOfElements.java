
package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.token.TokenImpl;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class NissaStewardOfElements extends CardImpl {

    public NissaStewardOfElements(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{X}{G}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.NISSA);

        this.setStartingLoyalty(-2); // -2 loyalty means X

        // +2: Scry 2.
        this.addAbility(new LoyaltyAbility(new ScryEffect(2), 2));

        // 0: Look at the top card of your library. If it's a land card or a creature card with converted mana cost less than or equal
        // to the number of loyalty counters on Nissa, Steward of Elements, you may put that card onto the battlefield.
        this.addAbility(new LoyaltyAbility(new NissaStewardOfElementsEffect(), 0));

        // -6: Untap up to two target lands you control. They become 5/5 Elemental creatures with flying and haste until end of turn. They're still lands.
        Effect effect = new UntapTargetEffect();
        effect.setText("Untap up to two target lands you control");
        LoyaltyAbility ability = new LoyaltyAbility(effect, -6);
        effect = new BecomesCreatureTargetEffect(new NissaStewardOfElementsToken(), false, true, Duration.EndOfTurn);
        effect.setText("They become 5/5 Elemental creatures with flying and haste until end of turn. They're still lands");
        ability.addEffect(effect);
        ability.addTarget(new TargetPermanent(0, 2, new FilterControlledLandPermanent(), false));
        this.addAbility(ability);
    }

    private NissaStewardOfElements(final NissaStewardOfElements card) {
        super(card);
    }

    @Override
    public NissaStewardOfElements copy() {
        return new NissaStewardOfElements(this);
    }
}

class NissaStewardOfElementsEffect extends OneShotEffect {

    public NissaStewardOfElementsEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "look at the top card of your library. If it's a land card or a creature card with mana value less than or equal "
                + "to the number of loyalty counters on {this}, you may put that card onto the battlefield";
    }

    public NissaStewardOfElementsEffect(final NissaStewardOfElementsEffect effect) {
        super(effect);
    }

    @Override
    public NissaStewardOfElementsEffect copy() {
        return new NissaStewardOfElementsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int count = 1 + new CountersSourceCount(CounterType.LOYALTY).calculate(game, source, this);
        FilterPermanentCard filter = new FilterPermanentCard();
        filter.add(Predicates.or(CardType.CREATURE.getPredicate(),
                CardType.LAND.getPredicate()));
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, count));
        Card card = controller.getLibrary().getFromTop(game);
        if (card != null) {
            controller.lookAtCards(source, null, new CardsImpl(card), game);
            if (filter.match(card, game)) {
                if (controller.chooseUse(outcome, "Put " + card.getName() + " onto the battlefield?", source, game)) {
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                }
            }
        }
        return true;
    }
}

class NissaStewardOfElementsToken extends TokenImpl {

    public NissaStewardOfElementsToken() {
        super("", "5/5 Elemental creature with flying and haste");
        this.cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());
    }

    public NissaStewardOfElementsToken(final NissaStewardOfElementsToken token) {
        super(token);
    }

    public NissaStewardOfElementsToken copy() {
        return new NissaStewardOfElementsToken(this);
    }
}

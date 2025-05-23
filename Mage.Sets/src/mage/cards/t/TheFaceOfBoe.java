package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.AbilityImpl;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheFaceOfBoe extends CardImpl {

    public TheFaceOfBoe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ALIEN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // {T}: You may cast a spell with suspend from your hand. If you do, pay its suspend cost rather than its mana cost. Activate only as a sorcery.
        this.addAbility(new ActivateAsSorceryActivatedAbility(new TheFaceOfBoeEffect(), new TapSourceCost()));
    }

    private TheFaceOfBoe(final TheFaceOfBoe card) {
        super(card);
    }

    @Override
    public TheFaceOfBoe copy() {
        return new TheFaceOfBoe(this);
    }
}

class TheFaceOfBoeEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("card with suspend");

    static {
        filter.add(new AbilityPredicate(SuspendAbility.class));
    }

    TheFaceOfBoeEffect() {
        super(Outcome.Benefit);
        staticText = "you may cast a spell with suspend from your hand. " +
                "If you do, pay its suspend cost rather than its mana cost";
    }

    private TheFaceOfBoeEffect(final TheFaceOfBoeEffect effect) {
        super(effect);
    }

    @Override
    public TheFaceOfBoeEffect copy() {
        return new TheFaceOfBoeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCard target = new TargetCardInHand(0, 1, filter);
        player.choose(outcome, player.getHand(), target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        ManaCostsImpl<ManaCost> cost = new ManaCostsImpl<>();
        CardUtil.castStream(card.getAbilities(game), SuspendAbility.class)
                .map(AbilityImpl::getManaCosts)
                .forEach(cost::addAll);
        CardUtil.castSingle(player, source, game, card, cost);
        return true;
    }
}

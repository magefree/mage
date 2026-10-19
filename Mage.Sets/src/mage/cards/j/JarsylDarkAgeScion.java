package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.IntensityAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

public final class JarsylDarkAgeScion extends CardImpl {

    public JarsylDarkAgeScion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(new IntensityAbility(1));
        this.addAbility(new BeginningOfCombatTriggeredAbility(new JarsylDarkAgeScionEffect()));
    }

    private JarsylDarkAgeScion(final JarsylDarkAgeScion card) {
        super(card);
    }

    @Override
    public JarsylDarkAgeScion copy() {
        return new JarsylDarkAgeScion(this);
    }
}

class JarsylDarkAgeScionEffect extends OneShotEffect {

    JarsylDarkAgeScionEffect() {
        super(Outcome.Benefit);
        staticText = "you may cast a spell with mana value equal to {this}'s intensity from your graveyard without paying its mana cost. If you do, Jarsyl intensifies by 1";
    }

    private JarsylDarkAgeScionEffect(final JarsylDarkAgeScionEffect effect) {
        super(effect);
    }

    @Override
    public JarsylDarkAgeScionEffect copy() {
        return new JarsylDarkAgeScionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = source.getSourcePermanentOrLKI(game);
        if (player == null || card == null) {
            return false;
        }
        FilterCard filter = new FilterCard();
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, IntensityAbility.getIntensity(card, game)));
        if (CardUtil.castSpellWithAttributesForFree(player, source, game, new CardsImpl(player.getGraveyard()), filter)) {
            IntensityAbility.intensify(card, 1, game);
            return true;
        }
        return false;
    }
}

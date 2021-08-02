package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CastFromEverywhereSourceCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Tiamat extends CardImpl {

    public Tiamat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}{B}{R}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.GOD);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Tiamat enters the battlefield, if you cast it, search your library for up to five Dragon cards named Tiama that each have different names, reveal them, put them into your hand, then shuffle.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutInHandEffect(new TiamatTarget(), true, true)),
                CastFromEverywhereSourceCondition.instance, "When {this} enters the battlefield, " +
                "if you cast it, search your library for up to five Dragon cards not named Tiamat " +
                "that each have different names, reveal them, put them into your hand, then shuffle."
        ));
    }

    private Tiamat(final Tiamat card) {
        super(card);
    }

    @Override
    public Tiamat copy() {
        return new Tiamat(this);
    }
}

class TiamatTarget extends TargetCardInLibrary {

    private static final FilterCard filter
            = new FilterCreatureCard("Dragon cards not named Tiamat that each have different names");

    static {
        filter.add(SubType.DRAGON.getPredicate());
        filter.add(Predicates.not(new NamePredicate("Tiamat")));
    }

    TiamatTarget() {
        super(0, 5, filter);
    }

    private TiamatTarget(final TiamatTarget target) {
        super(target);
    }

    @Override
    public TiamatTarget copy() {
        return new TiamatTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Cards cards, Game game) {
        Card card = cards.get(id, game);
        return card != null
                && filter.match(card, playerId, game)
                && this
                .getTargets()
                .stream()
                .map(game::getCard)
                .filter(Objects::nonNull)
                .map(Card::getName)
                .noneMatch(n -> CardUtil.haveSameNames(card, n, game));
    }
}

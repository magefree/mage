package mage.cards.p;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.OfferingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;


/**
 * @author LevelX2
 */
public final class PatronOfTheOrochi extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.SNAKE, "Snake");

    public PatronOfTheOrochi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{G}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Snake offering (You may cast this card any time you could cast an instant 
        // by sacrificing a Snake and paying the difference in mana costs between this 
        // and the sacrificed Snake. Mana cost includes color.)
        this.addAbility(new OfferingAbility(filter));

        // {T}: Untap all Forests and all green creatures. Activate this ability only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, new PatronOfTheOrochiEffect(), new TapSourceCost()));

    }

    private PatronOfTheOrochi(final PatronOfTheOrochi card) {
        super(card);
    }

    @Override
    public PatronOfTheOrochi copy() {
        return new PatronOfTheOrochi(this);
    }
}

class PatronOfTheOrochiEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(Predicates.or(SubType.FOREST.getPredicate(),
                Predicates.and(CardType.CREATURE.getPredicate(),
                        new ColorPredicate(ObjectColor.GREEN))
        ));
    }

    public PatronOfTheOrochiEffect() {
        super(Outcome.Untap);
        staticText = "Untap all Forests and all green creatures";
    }

    public PatronOfTheOrochiEffect(final PatronOfTheOrochiEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                permanent.untap(game);
            }
            return true;
        }
        return false;
    }

    @Override
    public PatronOfTheOrochiEffect copy() {
        return new PatronOfTheOrochiEffect(this);
    }
}

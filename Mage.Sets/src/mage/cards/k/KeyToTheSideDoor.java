package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author nandmp
 */
public final class KeyToTheSideDoor extends CardImpl {

    private static final FilterCard filter = new FilterCard(
            "a legendary card with the same name as a legendary permanent you control"
    );

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
        filter.add(KeyToTheSideDoorPredicate.instance);
    }

    public KeyToTheSideDoor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {2}, {T}: Target creature can't be blocked this turn.
        Ability ability = new SimpleActivatedAbility(
                new CantBeBlockedTargetEffect(), new GenericManaCost(2)
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // {1}, {T}, Discard a legendary card with the same name as a legendary permanent you control: Draw two cards.
        ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(2), new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardTargetCost(new TargetCardInHand(filter)));
        this.addAbility(ability);
    }

    private KeyToTheSideDoor(final KeyToTheSideDoor card) {
        super(card);
    }

    @Override
    public KeyToTheSideDoor copy() {
        return new KeyToTheSideDoor(this);
    }
}

enum KeyToTheSideDoorPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        return game.getBattlefield()
                .getAllActivePermanents(input.getPlayerId())
                .stream()
                .filter(permanent -> permanent.isLegendary(game))
                .anyMatch(permanent -> CardUtil.haveSameNames(
                        input.getObject(), permanent.getName(), game
                ));
    }
}

package mage.cards.o;

import java.util.UUID;

import mage.MageObject;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.CastFromHandWithoutPayingManaCostEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;

/**
 *
 * @author muz
 */
public final class Omnipresence extends CardImpl {

    private static final FilterCard filter
            = new FilterCard("spells with mana value less than or equal to the number of creatures you control");

    static {
        filter.add(ManaValueLessThanControlledCreatureCountPredicate.instance);
    }

    public Omnipresence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{5}{G}{G}{G}");

        // You may cast spells with mana value less than or equal to the number of creatures you control from your hand without paying their mana costs.
        this.addAbility(new SimpleStaticAbility(new CastFromHandWithoutPayingManaCostEffect(filter, true)));
    }

    private Omnipresence(final Omnipresence card) {
        super(card);
    }

    @Override
    public Omnipresence copy() {
        return new Omnipresence(this);
    }
}

enum ManaValueLessThanControlledCreatureCountPredicate implements ObjectSourcePlayerPredicate<MageObject> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        return input.getObject().getManaValue() <= game.getBattlefield().countAll(StaticFilters.FILTER_CONTROLLED_CREATURE, input.getPlayerId(), game);
    }

    @Override
    public String toString() {
        return "mana value less than or equal to the number of creatures you control";
    }
}

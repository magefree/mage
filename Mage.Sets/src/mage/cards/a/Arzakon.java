package mage.cards.a;

import java.util.UUID;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterAnyTarget;
import mage.filter.common.FilterPermanentOrPlayer;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.BlackLotusToken;
import mage.target.common.TargetPermanentOrPlayer;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.CanBeYourCommanderAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.ShuffleHandGraveyardAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class Arzakon extends CardImpl {

    private static final FilterPermanentOrPlayer filter = new FilterAnyTarget("any other target");

    static {
        filter.getPermanentFilter().add(AnotherPredicate.instance);
    }

    public Arzakon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{W}{U}{B}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ARZAKON);
        this.setStartingLoyalty(4);

        // +2: Arzakon deals 3 damage to any other target.
        LoyaltyAbility ability = new LoyaltyAbility(new DamageTargetEffect(3), 2);
        ability.addTarget(new TargetPermanentOrPlayer(filter));
        this.addAbility(ability);

        // −3: Create a Black Lotus token.
        LoyaltyAbility ability2 = new LoyaltyAbility(new CreateTokenEffect(new BlackLotusToken()), -3);
        this.addAbility(ability2);

        // −7: Each player shuffles their hand and graveyard into their library, then draws seven cards.
        LoyaltyAbility ability3 = new LoyaltyAbility(new ShuffleHandGraveyardAllEffect(), -7);
        ability3.addEffect(new DrawCardAllEffect(7).setText(", then draws seven cards"));
        this.addAbility(ability3);

        // Arzakon can be your commander.
        this.addAbility(CanBeYourCommanderAbility.getInstance());
    }

    private Arzakon(final Arzakon card) {
        super(card);
    }

    @Override
    public Arzakon copy() {
        return new Arzakon(this);
    }
}

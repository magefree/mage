package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.ModifiedPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.SpiritToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AkkiEmberKeeper extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("a nontoken modified creature you control");

    static {
        filter.add(TokenPredicate.FALSE);
        filter.add(ModifiedPredicate.instance);
    }

    public AkkiEmberKeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever a nontoken modified creature you control dies, create a 1/1 colorless Spirit creature token.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new CreateTokenEffect(new SpiritToken()), false, filter
        ));
    }

    private AkkiEmberKeeper(final AkkiEmberKeeper card) {
        super(card);
    }

    @Override
    public AkkiEmberKeeper copy() {
        return new AkkiEmberKeeper(this);
    }
}

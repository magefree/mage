package mage.cards.o;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.CardsLeaveGraveyardTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.ZombieWhiteToken;

/**
 *
 * @author Grath
 */
public final class OnWingsOfGold extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures you control that are Zombies and/or tokens");

    static {
        filter.add(Predicates.or(SubType.ZOMBIE.getPredicate(), TokenPredicate.TRUE));
    }

    public OnWingsOfGold(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // Creatures you control that are Zombies and/or tokens get +1/+1 and have flying.
        Ability ability = new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter, false
        ).setText("Creatures you control that are Zombies and/or tokens get +1/+1"));
        ability.addEffect(new GainAbilityControlledEffect(
                FlyingAbility.getInstance(), Duration.WhileOnBattlefield, filter
        ).setText("and have flying"));
        this.addAbility(ability);

        // Whenever one or more cards leave your graveyard, create a 1/1 white Zombie creature token.
        this.addAbility(new CardsLeaveGraveyardTriggeredAbility(
                new CreateTokenEffect(new ZombieWhiteToken(), 1, false, false)
        ));
    }

    private OnWingsOfGold(final OnWingsOfGold card) {
        super(card);
    }

    @Override
    public OnWingsOfGold copy() {
        return new OnWingsOfGold(this);
    }
}

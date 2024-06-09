package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.FoodToken;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class FoodComa extends CardImpl {

    public FoodComa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // When Food Coma enters the battlefield, exile target creature an opponent controls until Food Coma leaves the battlefield. Create a Food token.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect());
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        ability.addEffect(new CreateTokenEffect(new FoodToken()));
        this.addAbility(ability);

    }

    private FoodComa(final FoodComa card) {
        super(card);
    }

    @Override
    public FoodComa copy() {
        return new FoodComa(this);
    }
}

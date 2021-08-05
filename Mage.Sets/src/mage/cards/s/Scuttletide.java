package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.CrabToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Scuttletide extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.CRAB, "");

    public Scuttletide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        // {1}, Discard a card: Create a 0/3 blue Crab creature token.
        Ability ability = new SimpleActivatedAbility(new CreateTokenEffect(new CrabToken()), new GenericManaCost(1));
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);

        // Delirium — Crabs you control get +1/+1 as long as there are four or more card types among cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostControlledEffect(
                        1, 1, Duration.WhileOnBattlefield, filter, false
                ), DeliriumCondition.instance, "Crabs you control get +1/+1 as long " +
                "as there are four or more card types among cards in your graveyard"
        )).addHint(CardTypesInGraveyardHint.YOU).setAbilityWord(AbilityWord.DELIRIUM));
    }

    private Scuttletide(final Scuttletide card) {
        super(card);
    }

    @Override
    public Scuttletide copy() {
        return new Scuttletide(this);
    }
}

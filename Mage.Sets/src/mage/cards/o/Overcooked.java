package mage.cards.o;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CelebrationCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.ConjureCardEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.CantGainLifeAllEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.permanent.token.FoodToken;
import mage.watchers.common.PermanentsEnteredBattlefieldWatcher;

/**
 *
 * @author muz
 */
public final class Overcooked extends CardImpl {

    public Overcooked(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");


        // Players can't gain life.
        this.addAbility(new SimpleStaticAbility(new CantGainLifeAllEffect()));

        // Celebration -- At the beginning of your end step, create a Food token. If two or more nonland permanents entered under your control this turn, instead conjure a card named Food Fight onto the battlefield.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
            new ConditionalOneShotEffect(
                new ConjureCardEffect("Food Fight", Zone.BATTLEFIELD, 1),
                new CreateTokenEffect(new FoodToken()),
                CelebrationCondition.instance,
                "create a Food token. If two or more nonland permanents entered under your control this turn, instead conjure a card named Food Fight onto the battlefield"
            )
        );
        ability.setAbilityWord(AbilityWord.CELEBRATION);
        ability.addHint(CelebrationCondition.getHint());
        this.addAbility(ability, new PermanentsEnteredBattlefieldWatcher());
    }

    private Overcooked(final Overcooked card) {
        super(card);
    }

    @Override
    public Overcooked copy() {
        return new Overcooked(this);
    }
}

package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.PutIntoGraveFromBattlefieldSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.token.custom.CreatureToken;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WeatherseedTotem extends CardImpl {

    public WeatherseedTotem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {tap}: Add {G}.
        this.addAbility(new GreenManaAbility());

        // {2}{G}{G}{G}: Weatherseed Totem becomes a 5/3 green Treefolk artifact creature with trample until end of turn.
        this.addAbility(new SimpleActivatedAbility(new BecomesCreatureSourceEffect(
                new CreatureToken(5, 3, "5/3 green Treefolk artifact creature with trample")
                        .withColor("G")
                        .withSubType(SubType.TREEFOLK)
                        .withType(CardType.ARTIFACT)
                        .withAbility(TrampleAbility.getInstance()),
                CardType.ARTIFACT, Duration.EndOfTurn
        ), new ManaCostsImpl<>("{2}{G}{G}{G}")));

        // When Weatherseed Totem is put into a graveyard from the battlefield, if it was a creature, return this card to its owner's hand.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new PutIntoGraveFromBattlefieldSourceTriggeredAbility(new ReturnSourceFromGraveyardToHandEffect()),
                WeatherseedTotemCondition.instance, "When {this} is put into a graveyard from the battlefield, "
                + "if it was a creature, return this card to its owner's hand"
        ));
    }

    private WeatherseedTotem(final WeatherseedTotem card) {
        super(card);
    }

    @Override
    public WeatherseedTotem copy() {
        return new WeatherseedTotem(this);
    }
}

enum WeatherseedTotemCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return source
                .getEffects()
                .stream()
                .map(effect -> effect.getValue("permanentWasCreature"))
                .filter(Objects::nonNull)
                .anyMatch(Boolean.class::cast);
    }
}

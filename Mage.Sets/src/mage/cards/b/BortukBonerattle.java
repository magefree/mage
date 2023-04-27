package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CastFromEverywhereSourceCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.hint.common.DomainHint;
import mage.cards.Card;
import mage.constants.AbilityWord;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author weirddan455
 */
public final class BortukBonerattle extends CardImpl {

    public BortukBonerattle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.TROLL);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Domain — When Bortuk Bonerattle enters the battlefield, if you cast it, choose target creature card in your graveyard.
        // Return that card to the battlefield if its mana value is less than or equal to the number of basic land types among lands you control.
        // Otherwise, put it into your hand.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new ConditionalOneShotEffect(
                        new ReturnFromGraveyardToBattlefieldTargetEffect(),
                        new ReturnFromGraveyardToHandTargetEffect(),
                        BortukBonerattleCondition.instance,
                        null
                )),
                CastFromEverywhereSourceCondition.instance,
                "When {this} enters the battlefield, if you cast it, choose target creature card in your graveyard. " +
                        "Return that card to the battlefield if its mana value is less than or equal to the number of basic land types among lands you control. " +
                        "Otherwise, put it into your hand."
        );
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        ability.addHint(DomainHint.instance);
        ability.setAbilityWord(AbilityWord.DOMAIN);
        this.addAbility(ability);
    }

    private BortukBonerattle(final BortukBonerattle card) {
        super(card);
    }

    @Override
    public BortukBonerattle copy() {
        return new BortukBonerattle(this);
    }
}

enum BortukBonerattleCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getFirstTarget());
        return card != null && card.getManaValue() <= DomainValue.REGULAR.calculate(game, source, null);
    }

    @Override
    public String toString() {
        return "its mana value is less than or equal to the number of basic land types among lands you control";
    }
}

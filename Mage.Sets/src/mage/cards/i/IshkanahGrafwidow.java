package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.SpiderToken;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class IshkanahGrafwidow extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(
            new FilterControlledPermanent(SubType.SPIDER, "Spider you control")
    );
    private static final Hint hint = new ValueHint("Spiders you control", xValue);

    public IshkanahGrafwidow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIDER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // <i>Delirium</i> &mdash When Ishkanah, Grafwidow enters the battlefield, if there are four or more card types among cards in your graveyard,
        // create three 1/2 green Spider creature tokens with reach.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new CreateTokenEffect(new SpiderToken(), 3), false
        ).withInterveningIf(DeliriumCondition.instance)
                .setAbilityWord(AbilityWord.DELIRIUM)
                .addHint(CardTypesInGraveyardCount.YOU.getHint()));

        // {5}{B}: Target opponent loses 1 life for each Spider you control.
        Ability ability = new SimpleActivatedAbility(new LoseLifeTargetEffect(xValue), new ManaCostsImpl<>("{6}{B}"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability.addHint(hint));
    }

    private IshkanahGrafwidow(final IshkanahGrafwidow card) {
        super(card);
    }

    @Override
    public IshkanahGrafwidow copy() {
        return new IshkanahGrafwidow(this);
    }
}

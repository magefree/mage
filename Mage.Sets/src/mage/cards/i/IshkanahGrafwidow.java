package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
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

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.SPIDER, "Spider you control");

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
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new SpiderToken(), 3), false),
                DeliriumCondition.instance,
                "<i>Delirium</i> &mdash; When {this} enters, if there are four or more card types among cards in your graveyard, "
                        + "create three 1/2 green Spider creature tokens with reach.");
        ability.addHint(CardTypesInGraveyardCount.YOU.getHint());
        this.addAbility(ability);

        // {5}{B}: Target opponent loses 1 life for each Spider you control.
        PermanentsOnBattlefieldCount count = new PermanentsOnBattlefieldCount(filter);
        ability = new SimpleActivatedAbility(new LoseLifeTargetEffect(count), new ManaCostsImpl<>("{6}{B}"));
        ability.addTarget(new TargetOpponent());
        ability.addHint(new ValueHint("Spiders you control", count));
        this.addAbility(ability);
    }

    private IshkanahGrafwidow(final IshkanahGrafwidow card) {
        super(card);
    }

    @Override
    public IshkanahGrafwidow copy() {
        return new IshkanahGrafwidow(this);
    }
}

package mage.cards.m;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.token.PestBlackGreenAttacksToken;
import mage.target.common.TargetCardInYourGraveyard;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class MoseoVeinsNewDean extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard(
            "creature card with mana value X or less"
    );

    static {
        filter.add(MoseoVeinsNewDeanPredicate.instance);
    }

    public MoseoVeinsNewDean(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SKELETON);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Moseo enters, create a 1/1 black and green Pest creature token with "Whenever this token attacks, you gain 1 life."
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new PestBlackGreenAttacksToken())));

        // Infusion -- At the beginning of your end step, if you gained life this turn, return up to one target creature card with mana value X or less from your graveyard to the battlefield, where X is the amount of life you gained this turn.
        TriggeredAbility ability = new BeginningOfEndStepTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect()
                .setText("return up to one target creature card with mana value X or less " +
                        "from your graveyard to the battlefield, " +
                        "where X is the amount of life you gained this turn"));
        ability.addTarget(new TargetCardInYourGraveyard(0, 1, filter));
        ability.withInterveningIf(YouGainedLifeCondition.getZero());
        ability.setAbilityWord(AbilityWord.INFUSION);
        ability.addHint(ControllerGainedLifeCount.getHint());
        this.addAbility(ability, new PlayerGainedLifeWatcher());
    }

    private MoseoVeinsNewDean(final MoseoVeinsNewDean card) {
        super(card);
    }

    @Override
    public MoseoVeinsNewDean copy() {
        return new MoseoVeinsNewDean(this);
    }
}

enum MoseoVeinsNewDeanPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        return input.getObject().getManaValue()
                <= ControllerGainedLifeCount.instance.calculate(game, input.getSource(), null);
    }
}

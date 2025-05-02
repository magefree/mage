package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.InsectBlackGreenFlyingToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheSwarmweaver extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();
    private static final FilterPermanent filter2 = new FilterPermanent();
    private static final Predicate<Permanent> predicate = Predicates.or(
            SubType.INSECT.getPredicate(),
            SubType.SPIDER.getPredicate()
    );

    static {
        filter.add(predicate);
        filter2.add(predicate);
    }

    public TheSwarmweaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SCARECROW);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When The Swarmweaver enters, create two 1/1 black and green Insect creature tokens with flying.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new InsectBlackGreenFlyingToken(), 2)));

        // Delirium -- As long as there are four or more card types among cards in your graveyard, Insects and Spiders you control get +1/+1 and have deathtouch.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter),
                DeliriumCondition.instance, "as long as there are four or more card types " +
                "among cards in your graveyard, Insects and Spiders you control get +1/+1"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilityControlledEffect(
                        DeathtouchAbility.getInstance(), Duration.WhileOnBattlefield, filter2
                ), DeliriumCondition.instance, "and have deathtouch"
        ));
        this.addAbility(ability.setAbilityWord(AbilityWord.DELIRIUM).addHint(CardTypesInGraveyardCount.YOU.getHint()));
    }

    private TheSwarmweaver(final TheSwarmweaver card) {
        super(card);
    }

    @Override
    public TheSwarmweaver copy() {
        return new TheSwarmweaver(this);
    }
}

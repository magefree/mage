package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.AddCardTypeTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.abilities.token.FoodAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.FoodToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ToughCookie extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledArtifactPermanent("noncreature artifact you control");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public ToughCookie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.FOOD);
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Tough Cookie enters the battlefield, create a Food token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new FoodToken())));

        // {2}{G}: Target noncreature artifact you control becomes a 4/4 artifact creature until end of turn.
        Ability ability = new SimpleActivatedAbility(new AddCardTypeTargetEffect(
                Duration.EndOfTurn, CardType.ARTIFACT, CardType.CREATURE
        ).setText("target noncreature artifact you control becomes"), new ManaCostsImpl<>("{2}{G}"));
        ability.addEffect(new SetBasePowerToughnessTargetEffect(
                4, 4, Duration.EndOfTurn
        ).setText(" a 4/4 artifact creature until end of turn"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // {2}, {T}, Sacrifice Tough Cookie: You gain 3 life.
        this.addAbility(new FoodAbility(true));
    }

    private ToughCookie(final ToughCookie card) {
        super(card);
    }

    @Override
    public ToughCookie copy() {
        return new ToughCookie(this);
    }
}

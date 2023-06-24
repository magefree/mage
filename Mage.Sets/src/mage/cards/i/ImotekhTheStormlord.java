package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.CardsLeaveGraveyardTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.NecronWarriorToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ImotekhTheStormlord extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("another target artifact creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public ImotekhTheStormlord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.NECRON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Phaeron -- Whenever one or more artifact cards leave your graveyard, create two 2/2 black Necron Warrior artifact creature tokens.
        this.addAbility(new CardsLeaveGraveyardTriggeredAbility(
                new CreateTokenEffect(new NecronWarriorToken(), 2),
                StaticFilters.FILTER_CARD_ARTIFACTS
        ).withFlavorWord("Phaeron"));

        // Grand Strategist -- At the beginning of combat on your turn, another target artifact creature you control gets +2/+2 and gains menace until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new BoostTargetEffect(2, 2)
                        .setText("another target artifact creature you control gets +2/+2"),
                TargetController.YOU, false
        );
        ability.addEffect(new GainAbilityTargetEffect(new MenaceAbility(false))
                .setText("and gains menace until end of turn"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability.withFlavorWord("Grand Strategist"));
    }

    private ImotekhTheStormlord(final ImotekhTheStormlord card) {
        super(card);
    }

    @Override
    public ImotekhTheStormlord copy() {
        return new ImotekhTheStormlord(this);
    }
}

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CardsLeaveGraveyardTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.SkeletonPirateToken;

/**
 * @author arcox
 */
public final class SkeletonCrew extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("each other creature you control that's a Skeleton or Pirate");

    static {
        filter.add(Predicates.or(SubType.SKELETON.getPredicate(), SubType.PIRATE.getPredicate()));
    }

    public SkeletonCrew(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.SKELETON);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Each other creature you control that’s a Skeleton or Pirate gets +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter, true)));

        // Whenever one or more creature cards leave your graveyard, create a 2/2 black Skeleton Pirate creature token.
        this.addAbility(new CardsLeaveGraveyardTriggeredAbility(
                new CreateTokenEffect(new SkeletonPirateToken()), StaticFilters.FILTER_CARD_CREATURES
        ));

        // {5}{B}: Return Skeleton Crew from your graveyard to the battlefield tapped.
        this.addAbility(new SimpleActivatedAbility(
                Zone.GRAVEYARD, new ReturnSourceFromGraveyardToBattlefieldEffect(true, false),
                new ManaCostsImpl<>("{5}{B}")
        ));
    }

    private SkeletonCrew(final SkeletonCrew card) {
        super(card);
    }

    @Override
    public SkeletonCrew copy() {
        return new SkeletonCrew(this);
    }
}

package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInOpponentsGraveyard;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GeneralKudroOfDrannith extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent(SubType.HUMAN, "Humans");
    private static final FilterPermanent filter2
            = new FilterPermanent(SubType.HUMAN, "Human");
    private static final FilterCard filter3
            = new FilterCard("card from an opponent's graveyard");
    private static final FilterControlledPermanent filter4
            = new FilterControlledPermanent(SubType.HUMAN, "Humans");
    private static final FilterCreaturePermanent filter5
            = new FilterCreaturePermanent("creature with power 4 or greater");

    static {
        filter5.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }

    public GeneralKudroOfDrannith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Other Humans you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter, true
        )));

        // Whenever General Kudro of Drannith or another Human enters the battlefield under your control, exile target card from an opponent's graveyard.
        Ability ability = new EntersBattlefieldThisOrAnotherTriggeredAbility(new ExileTargetEffect(), filter2, false, true);
        ability.addTarget(new TargetCardInOpponentsGraveyard(filter3));
        this.addAbility(ability);

        // {2}, Sacrifice two Humans: Destroy target creature with power 4 or greater.
        ability = new SimpleActivatedAbility(new DestroyTargetEffect(), new GenericManaCost(2));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(2, filter4)));
        ability.addTarget(new TargetPermanent(filter5));
        this.addAbility(ability);
    }

    private GeneralKudroOfDrannith(final GeneralKudroOfDrannith card) {
        super(card);
    }

    @Override
    public GeneralKudroOfDrannith copy() {
        return new GeneralKudroOfDrannith(this);
    }
}

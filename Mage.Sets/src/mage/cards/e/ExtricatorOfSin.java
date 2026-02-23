package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.EldraziHorrorToken;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ExtricatorOfSin extends TransformingDoubleFacedCard {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("another permanent");
    private static final FilterControlledCreaturePermanent filterNonEldrazi = new FilterControlledCreaturePermanent("non-Eldrazi creature");
    private static final FilterControlledPermanent filterEldrazi = new FilterControlledPermanent(SubType.ELDRAZI, "Eldrazi");

    static {
        filter.add(AnotherPredicate.instance);
        filterNonEldrazi.add(Predicates.not(SubType.ELDRAZI.getPredicate()));
    }

    public ExtricatorOfSin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.CLERIC}, "{2}{W}",
                "Extricator Of Flesh",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELDRAZI, SubType.HORROR}, ""
        );

        // Extricator of Sin
        this.getLeftHalfCard().setPT(0, 3);

        // When Extricator of Sin enters the battlefield, you may sacrifice another permanent. If you do, create a 3/2 colorless Eldrazi Horror creature token.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new DoIfCostPaid(new CreateTokenEffect(new EldraziHorrorToken()),
                new SacrificeTargetCost(filter)), false));

        // Delirium — At the beginning of your upkeep, if there are four or more card types among cards in your graveyard, transform Extricator of Sin.
        this.getLeftHalfCard().addAbility(new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect())
                .withInterveningIf(DeliriumCondition.instance)
                .setAbilityWord(AbilityWord.DELIRIUM)
                .addHint(CardTypesInGraveyardCount.YOU.getHint()));

        // Extricator Of Flesh
        this.getRightHalfCard().setPT(3, 5);

        // Eldrazi you control have vigilance
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                VigilanceAbility.getInstance(), Duration.WhileOnBattlefield, filterEldrazi)));

        // {2}, {T}, Sacrifice a non-Eldrazi creature: Create a 3/2 colorless Eldrazi Horror creature token.
        Ability ability = new SimpleActivatedAbility(new CreateTokenEffect(new EldraziHorrorToken()), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(filterNonEldrazi));
        this.getRightHalfCard().addAbility(ability);
    }

    private ExtricatorOfSin(final ExtricatorOfSin card) {
        super(card);
    }

    @Override
    public ExtricatorOfSin copy() {
        return new ExtricatorOfSin(this);
    }
}

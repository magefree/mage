package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterPermanent;
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
    private static final FilterPermanent filter2 = new FilterPermanent(SubType.ELDRAZI, "Eldrazi");
    private static final FilterControlledPermanent filter3 = new FilterControlledCreaturePermanent("non-Eldrazi creature");

    static {
        filter.add(AnotherPredicate.instance);
        filter3.add(Predicates.not(SubType.ELDRAZI.getPredicate()));
    }

    public ExtricatorOfSin(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.CLERIC}, "{2}{W}",
                "Extricator of Flesh",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELDRAZI, SubType.HORROR}, ""
        );
        this.getLeftHalfCard().setPT(0, 3);
        this.getRightHalfCard().setPT(3, 5);

        // When Extricator of Sin enters the battlefield, you may sacrifice another permanent. If you do, create a 3/2 colorless Eldrazi Horror creature token.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new DoIfCostPaid(
                new CreateTokenEffect(new EldraziHorrorToken()), new SacrificeTargetCost(filter)
        ), false));

        // <i>Delirium</i> &mdash; At the beginning of your upkeep, if there are four or more card types among cards in your graveyard, transform Extricator of Sin.
        this.getLeftHalfCard().addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(
                        new TransformSourceEffect(), TargetController.YOU, false
                ), DeliriumCondition.instance, "At the beginning of your upkeep, if there are " +
                "four or more card types among cards in your graveyard,  transform {this}."
        ).addHint(CardTypesInGraveyardHint.YOU).setAbilityWord(AbilityWord.DELIRIUM));

        // Extricator of Flesh
        // Eldrazi you control have vigilance
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                VigilanceAbility.getInstance(), Duration.WhileOnBattlefield, filter2
        )));

        // {2}, {T}, Sacrifice a non-Eldrazi creature: Create a 3/2 colorless Eldrazi Horror creature token.
        Ability ability = new SimpleActivatedAbility(
                new CreateTokenEffect(new EldraziHorrorToken()), new GenericManaCost(2)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(filter3));
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

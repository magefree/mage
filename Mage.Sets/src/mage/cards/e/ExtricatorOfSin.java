package mage.cards.e;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.EldraziHorrorToken;
import mage.target.common.TargetControlledPermanent;

/**
 * @author LevelX2
 */
public final class ExtricatorOfSin extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("another permanent");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public ExtricatorOfSin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        this.secondSideCardClazz = mage.cards.e.ExtricatorOfFlesh.class;

        // When Extricator of Sin enters the battlefield, you may sacrifice another permanent. If you do, create a 3/2 colorless Eldrazi Horror creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoIfCostPaid(new CreateTokenEffect(new EldraziHorrorToken()),
                new SacrificeTargetCost(new TargetControlledPermanent(filter))), false));

        // <i>Delirium</i> &mdash; At the beginning of your upkeep, if there are four or more card types among cards in your graveyard, transform Extricator of Sin.
        this.addAbility(new TransformAbility());
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new TransformSourceEffect(), TargetController.YOU, false),
                DeliriumCondition.instance,
                "<i>Delirium</i> &mdash; At the beginning of your upkeep, if there are four or more card types among cards in your graveyard, "
                        + " transform {this}.")
                .addHint(CardTypesInGraveyardHint.YOU));
    }

    private ExtricatorOfSin(final ExtricatorOfSin card) {
        super(card);
    }

    @Override
    public ExtricatorOfSin copy() {
        return new ExtricatorOfSin(this);
    }
}

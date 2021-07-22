package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CastFromHandSourcePermanentCondition;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.FoodToken;
import mage.target.common.TargetControlledPermanent;
import mage.watchers.common.CastFromHandWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FeastingTrollKing extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.FOOD, "Foods");

    public FeastingTrollKing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}{G}{G}");

        this.subtype.add(SubType.TROLL);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(7);
        this.toughness = new MageInt(6);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Feasting Troll King enters the battlefield, if you cast it from your hand, create three Food tokens.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new FoodToken(), 3)),
                CastFromHandSourcePermanentCondition.instance, "When {this} enters the battlefield, " +
                "if you cast it from your hand, create three Food tokens."
        ), new CastFromHandWatcher());

        // Sacrifice three Foods: Return Feasting Troll King from your graveyard to the battlefield. Activate this ability only during your turn.
        this.addAbility(new ActivateIfConditionActivatedAbility(
                Zone.GRAVEYARD,
                new ReturnSourceFromGraveyardToBattlefieldEffect(false, false),
                new SacrificeTargetCost(new TargetControlledPermanent(3, filter)),
                MyTurnCondition.instance
        ).addHint(MyTurnHint.instance));
    }

    private FeastingTrollKing(final FeastingTrollKing card) {
        super(card);
    }

    @Override
    public FeastingTrollKing copy() {
        return new FeastingTrollKing(this);
    }
}

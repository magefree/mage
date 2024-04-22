package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.BeginningOfPreCombatMainTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.permanent.token.Tyranid55Token;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OldOneEye extends CardImpl {

    public OldOneEye(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TYRANID);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Other creatures you control have trample.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_CREATURES, true
        )));

        // When Old One Eye enters the battlefield, create a 5/5 green Tyranid creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new Tyranid55Token())));

        // Fast Healing -- At the beginning of your precombat main phase, you may discard two cards. If you do, return Old One Eye from your graveyard to your hand.
        this.addAbility(new BeginningOfPreCombatMainTriggeredAbility(
                Zone.GRAVEYARD,
                new DoIfCostPaid(
                        new ReturnSourceFromGraveyardToHandEffect(),
                        new DiscardTargetCost(new TargetCardInHand(2, StaticFilters.FILTER_CARD_CARDS))
                ), TargetController.YOU, false, false
        ).withFlavorWord("Fast Healing"));
    }

    private OldOneEye(final OldOneEye card) {
        super(card);
    }

    @Override
    public OldOneEye copy() {
        return new OldOneEye(this);
    }
}

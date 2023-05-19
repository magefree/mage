package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.TransformsOrEntersTriggeredAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledToBattlefieldAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.NightboundAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrutalCathar extends TransformingDoubleFacedCard {

    public BrutalCathar(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.SOLDIER, SubType.WEREWOLF}, "{2}{W}",
                "Moonrage Brute",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "R"
        );
        this.getLeftHalfCard().setPT(2, 2);
        this.getRightHalfCard().setPT(3, 3);

        // When this creature enters the battlefield or transforms into Brutal Cathar, exile target creature an opponent controls until this creature leaves the battlefield.
        Ability ability = new TransformsOrEntersTriggeredAbility(
                new ExileUntilSourceLeavesEffect(), false
        ).setTriggerPhrase("When this creature enters the battlefield or transforms into {this}, ");
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledToBattlefieldAbility()));
        this.getLeftHalfCard().addAbility(ability);

        // Daybound
        this.getLeftHalfCard().addAbility(new DayboundAbility());

        // Moonrage Brute
        // First strike
        this.getRightHalfCard().addAbility(FirstStrikeAbility.getInstance());

        // Ward—Pay 3 life.
        this.getRightHalfCard().addAbility(new WardAbility(new PayLifeCost(3), false));

        // Nightbound
        this.getRightHalfCard().addAbility(new NightboundAbility());
    }

    private BrutalCathar(final BrutalCathar card) {
        super(card);
    }

    @Override
    public BrutalCathar copy() {
        return new BrutalCathar(this);
    }
}

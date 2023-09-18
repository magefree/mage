package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.BloodToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BloodtitheHarvester extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.BLOOD);

    static {
        filter.add(TokenPredicate.TRUE);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, -2);
    private static final Hint hint = new ValueHint(
            "Blood tokens you control", new PermanentsOnBattlefieldCount(filter)
    );

    public BloodtitheHarvester(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{R}");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Bloodtithe Harvester enters the battlefield, create a Blood token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new BloodToken())));

        // {T}, Sacrifice Bloodtithe Harvester: Target creature gets -X/-X until end of turn, where X is twice the number of Blood tokens you control. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new BoostTargetEffect(
                xValue, xValue, Duration.EndOfTurn
        ).setText("target creature gets -X/-X until end of turn, where X is twice the number of Blood tokens you control"), new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability.addHint(hint));
    }

    private BloodtitheHarvester(final BloodtitheHarvester card) {
        super(card);
    }

    @Override
    public BloodtitheHarvester copy() {
        return new BloodtitheHarvester(this);
    }
}

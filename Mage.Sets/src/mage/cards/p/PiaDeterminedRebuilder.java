package mage.cards.p;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.ThopterColorlessToken;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class PiaDeterminedRebuilder extends CardImpl {

    private static final PermanentsOnBattlefieldCount artifactsCount = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACTS);

    public PiaDeterminedRebuilder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Pia enters, create a 1/1 colorless Thopter artifact creature token with flying.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new ThopterColorlessToken())));

        // {5}{R}: Target creature gets +X/+0 until end of turn, where X is the number of artifacts you control.
        Ability ability = new SimpleActivatedAbility(
            new BoostTargetEffect(artifactsCount, StaticValue.get(0)), new ManaCostsImpl<>("{5}{R}")
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private PiaDeterminedRebuilder(final PiaDeterminedRebuilder card) {
        super(card);
    }

    @Override
    public PiaDeterminedRebuilder copy() {
        return new PiaDeterminedRebuilder(this);
    }
}

package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class Fleshformer extends CardImpl {

    public Fleshformer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {W}{U}{B}{R}{G}: Fleshformer gets +2/+2 and gains fear until end of turn. Target creature gets -2/-2 until end of turn. Activate this ability only during your turn.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new BoostSourceEffect(2, 2, Duration.EndOfTurn)
                        .setText("{this} gets +2/+2"),
                new ManaCostsImpl<>("{W}{U}{B}{R}{G}"), MyTurnCondition.instance
        );
        ability.addEffect(new GainAbilitySourceEffect(FearAbility.getInstance(), Duration.EndOfTurn).setText("and gains fear until end of turn"));
        ability.addEffect(new BoostTargetEffect(-2, -2, Duration.EndOfTurn));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private Fleshformer(final Fleshformer card) {
        super(card);
    }

    @Override
    public Fleshformer copy() {
        return new Fleshformer(this);
    }
}

package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.MinotaurToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SethronHurloonGeneral extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent(SubType.MINOTAUR, "nontoken Minotaur");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent(SubType.MINOTAUR, "");
    private static final FilterPermanent filter3 = new FilterPermanent(SubType.MINOTAUR, "");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public SethronHurloonGeneral(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Sethron, Hurloon General or another nontoken Minotaur enters the battlefield under your control, create a 2/3 red Minotaur creature token.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new CreateTokenEffect(new MinotaurToken()), filter, false, true
        ));

        // {2}{B/R}: Minotaurs you control get +1/+0 and gain menace and haste until end of turn.
        Ability ability = new SimpleActivatedAbility(new BoostControlledEffect(
                1, 0, Duration.EndOfTurn, filter2
        ).setText("Minotaurs you control get +1/+0"), new ManaCostsImpl<>("{2}{B/R}"));
        ability.addEffect(new GainAbilityControlledEffect(
                new MenaceAbility(), Duration.EndOfTurn, filter3
        ).setText("and gain menace"));
        ability.addEffect(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn, filter3
        ).setText("and haste until end of turn"));
        this.addAbility(ability);
    }

    private SethronHurloonGeneral(final SethronHurloonGeneral card) {
        super(card);
    }

    @Override
    public SethronHurloonGeneral copy() {
        return new SethronHurloonGeneral(this);
    }
}

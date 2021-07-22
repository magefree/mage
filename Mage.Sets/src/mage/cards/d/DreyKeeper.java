package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.SquirrelToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DreyKeeper extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.SQUIRREL, "");

    public DreyKeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Drey Keeper enters the battlefield, create two 1/1 green Squirrel creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new SquirrelToken(), 2)));

        // {3}{B}: Squirrels you control get +1/+0 and gain menace until end of turn.
        Ability ability = new SimpleActivatedAbility(new BoostControlledEffect(
                1, 0, Duration.EndOfTurn, filter
        ).setText("squirrels you control get +1/+0"), new ManaCostsImpl<>("{3}{B}"));
        ability.addEffect(new GainAbilityControlledEffect(
                new MenaceAbility(), Duration.EndOfTurn, filter
        ).setText("and gain menace until end of turn"));
        this.addAbility(ability);
    }

    private DreyKeeper(final DreyKeeper card) {
        super(card);
    }

    @Override
    public DreyKeeper copy() {
        return new DreyKeeper(this);
    }
}

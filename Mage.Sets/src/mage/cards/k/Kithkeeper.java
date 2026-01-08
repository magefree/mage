package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.dynamicvalue.common.ColorsAmongControlledPermanentsCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.KithkinGreenWhiteToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Kithkeeper extends CardImpl {

    public Kithkeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{W}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vivid -- When this creature enters, create X 1/1 green and white Kithkin creature tokens, where X is the number of colors among permanents you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(
                new KithkinGreenWhiteToken(), ColorsAmongControlledPermanentsCount.ALL_PERMANENTS
        )).setAbilityWord(AbilityWord.VIVID).addHint(ColorsAmongControlledPermanentsCount.ALL_PERMANENTS.getHint()));

        // Tap three untapped creatures you control: This creature gets +3/+0 and gains flying until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new BoostSourceEffect(3, 0, Duration.EndOfTurn)
                        .setText("{this} gets +3/+0"),
                new TapTargetCost(3, StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURES)
        );
        ability.addEffect(new GainAbilitySourceEffect(
                FlyingAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains flying until end of turn"));
        this.addAbility(ability);
    }

    private Kithkeeper(final Kithkeeper card) {
        super(card);
    }

    @Override
    public Kithkeeper copy() {
        return new Kithkeeper(this);
    }
}

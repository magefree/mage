package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.KnightToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ImodanesRecruiter extends AdventureCard {

    public ImodanesRecruiter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{2}{R}", "Train Troops", "{4}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Imodane's Recruiter enters the battlefield, creatures you control get +1/+0 and gain haste until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new BoostControlledEffect(1, 0, Duration.EndOfTurn)
                        .setText("creatures you control get +1/+0")
        );
        ability.addEffect(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURES
        ).setText("and gain haste until end of turn"));
        this.addAbility(ability);

        // Train Troops
        // Create two 2/2 white Knight creature tokens with vigilance.
        this.getSpellCard().getSpellAbility().addEffect(new CreateTokenEffect(new KnightToken(), 2));

        this.finalizeAdventure();
    }

    private ImodanesRecruiter(final ImodanesRecruiter card) {
        super(card);
    }

    @Override
    public ImodanesRecruiter copy() {
        return new ImodanesRecruiter(this);
    }
}

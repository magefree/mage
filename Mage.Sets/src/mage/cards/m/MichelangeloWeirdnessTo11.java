package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.replacement.ModifyCountersAddedEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.MutagenToken;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class MichelangeloWeirdnessTo11 extends CardImpl {

    public MichelangeloWeirdnessTo11(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Michelangelo enters, create a Mutagen token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new MutagenToken())));

        // If one or more +1/+1 counters would be put on a creature you control, that many plus one +1/+1 are put on it instead.
        this.addAbility(new SimpleStaticAbility(new ModifyCountersAddedEffect(StaticFilters.FILTER_CONTROLLED_CREATURE, CounterType.P1P1)));
    }

    private MichelangeloWeirdnessTo11(final MichelangeloWeirdnessTo11 card) {
        super(card);
    }

    @Override
    public MichelangeloWeirdnessTo11 copy() {
        return new MichelangeloWeirdnessTo11(this);
    }
}

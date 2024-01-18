package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.common.counter.AddPoisonCounterTargetEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PersuasiveInterrogators extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.CLUE, "a Clue");

    public PersuasiveInterrogators(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.subtype.add(SubType.GORGON);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // When Persuasive Interrogators enters the battlefield, investigate.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new InvestigateEffect()));

        // Whenever you sacrifice a Clue, target opponent gets two poison counters.
        Ability ability = new SacrificePermanentTriggeredAbility(new AddPoisonCounterTargetEffect(2), filter);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private PersuasiveInterrogators(final PersuasiveInterrogators card) {
        super(card);
    }

    @Override
    public PersuasiveInterrogators copy() {
        return new PersuasiveInterrogators(this);
    }
}

package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.ExileReturnBattlefieldOwnerNextEndStepSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DjinnOfTheFountain extends CardImpl {

    public DjinnOfTheFountain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");

        this.subtype.add(SubType.DJINN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast an instant or sorcery spell, choose one --
        // * Djinn of the Fountain gets +1/+1 until end of turn.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new BoostSourceEffect(1, 1, Duration.EndOfTurn),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        );

        // * Exile Djinn of the Fountain. Return it to the battlefield under its owner's control at the beginning of the next end step.
        ability.addMode(new Mode(new ExileReturnBattlefieldOwnerNextEndStepSourceEffect()));

        // * Scry 1.
        ability.addMode(new Mode(new ScryEffect(1, false)));
        this.addAbility(ability);
    }

    private DjinnOfTheFountain(final DjinnOfTheFountain card) {
        super(card);
    }

    @Override
    public DjinnOfTheFountain copy() {
        return new DjinnOfTheFountain(this);
    }
}

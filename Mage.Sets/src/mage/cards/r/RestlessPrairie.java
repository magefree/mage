package mage.cards.r;

import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.custom.CreatureToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class RestlessPrairie extends CardImpl {

    public RestlessPrairie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Restless Prairie enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {G} or {W}.
        this.addAbility(new GreenManaAbility());
        this.addAbility(new WhiteManaAbility());

        // {2}{G}{W}: Restless Prairie becomes a 3/3 green and white Llama creature until end of turn. It's still a land.
        this.addAbility(new SimpleActivatedAbility(new BecomesCreatureSourceEffect(
                new CreatureToken(3, 3, "3/3 green and white Llama creature")
                        .withColor("GW").withSubType(SubType.LLAMA),
                CardType.LAND, Duration.EndOfTurn
        ), new ManaCostsImpl<>("{2}{G}{W}")));

        // Whenever Restless Prairie attacks, other creatures you control get +1/+1 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(
                new BoostControlledEffect(
                        1, 1, Duration.EndOfTurn,
                        StaticFilters.FILTER_PERMANENT_CREATURES, true
                ), false
        ));
    }

    private RestlessPrairie(final RestlessPrairie card) {
        super(card);
    }

    @Override
    public RestlessPrairie copy() {
        return new RestlessPrairie(this);
    }
}

package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeDefendingPlayerEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.permanent.token.custom.CreatureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RestlessFortress extends CardImpl {

    public RestlessFortress(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Restless Fortress enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {W} or {B}.
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlackManaAbility());

        // {2}{W}{B}: Restless Fortress becomes a 1/4 white and black Nightmare creature until end of turn. It's still a land.
        this.addAbility(new SimpleActivatedAbility(new BecomesCreatureSourceEffect(
                new CreatureToken(1, 4, "1/4 white and black Nightmare creature")
                        .withColor("WB").withSubType(SubType.NIGHTMARE),
                CardType.LAND, Duration.EndOfTurn
        ), new ManaCostsImpl<>("{2}{W}{B}")));

        // Whenever Restless Fortress attacks, defending player loses 2 life and you gain 2 life.
        Ability ability = new AttacksTriggeredAbility(new LoseLifeDefendingPlayerEffect(2, true), false);
        ability.addEffect(new GainLifeEffect(2).concatBy("and"));
        this.addAbility(ability);
    }

    private RestlessFortress(final RestlessFortress card) {
        super(card);
    }

    @Override
    public RestlessFortress copy() {
        return new RestlessFortress(this);
    }
}

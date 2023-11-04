package mage.cards.r;

import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.permanent.token.MapToken;
import mage.game.permanent.token.custom.CreatureToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class RestlessAnchorage extends CardImpl {

    public RestlessAnchorage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Restless Anchorage enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {W} or {U}
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlueManaAbility());

        // {1}{W}{U}: Until end of turn, Restless Anchorage becomes a 2/3 white and blue Bird creature with flying. It's still a land.
        this.addAbility(new SimpleActivatedAbility(new BecomesCreatureSourceEffect(
                new CreatureToken(2, 3, "2/3 white and blue Bird creature with flying")
                        .withColor("WU")
                        .withSubType(SubType.BIRD)
                        .withAbility(FlyingAbility.getInstance()),
                CardType.LAND, Duration.EndOfTurn
        ).withDurationRuleAtStart(true), new ManaCostsImpl<>("{1}{W}{U}")));

        // Whenever Restless Anchorage attacks, create a Map token.
        this.addAbility(new AttacksTriggeredAbility(
                new CreateTokenEffect(new MapToken())
        ));
    }

    private RestlessAnchorage(final RestlessAnchorage card) {
        super(card);
    }

    @Override
    public RestlessAnchorage copy() {
        return new RestlessAnchorage(this);
    }
}

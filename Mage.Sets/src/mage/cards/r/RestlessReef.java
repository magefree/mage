package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class RestlessReef extends CardImpl {

    public RestlessReef(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Restless Reef enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {U} or {B}.
        this.addAbility(new BlueManaAbility());
        this.addAbility(new BlackManaAbility());

        // {2}{U}{B}: Until end of turn, Restless Reef becomes a 4/4 blue and black Shark creature with deathtouch. It's still a land.
        this.addAbility(new SimpleActivatedAbility(new BecomesCreatureSourceEffect(
                new CreatureToken(4, 4, "4/4 blue and black Shark creature with deathtouch")
                        .withColor("UB")
                        .withSubType(SubType.SHARK)
                        .withAbility(DeathtouchAbility.getInstance()),
                CardType.LAND, Duration.EndOfTurn
        ).withDurationRuleAtStart(true), new ManaCostsImpl<>("{2}{U}{B}")));

        // Whenever Restless Reef attacks, target player mills 4 cards.
        Ability ability = new AttacksTriggeredAbility(new MillCardsTargetEffect(4));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private RestlessReef(final RestlessReef card) {
        super(card);
    }

    @Override
    public RestlessReef copy() {
        return new RestlessReef(this);
    }
}

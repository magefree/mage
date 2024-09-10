package mage.cards.r;

import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.permanent.token.custom.CreatureToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class RestlessVents extends CardImpl {

    public RestlessVents(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Restless Vents enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {B} or {R}.
        this.addAbility(new BlackManaAbility());
        this.addAbility(new RedManaAbility());

        // {1}{B}{R}: Until end of turn, Restless Vents becomes a 2/3 black and red Insect creature with menace. It's still a land.
        this.addAbility(new SimpleActivatedAbility(new BecomesCreatureSourceEffect(
                new CreatureToken(2, 3, "2/3 black and red Insect creature with menace")
                        .withColor("BR")
                        .withSubType(SubType.INSECT)
                        .withAbility(new MenaceAbility(false)),
                CardType.LAND, Duration.EndOfTurn
        ).withDurationRuleAtStart(true), new ManaCostsImpl<>("{1}{B}{R}")));

        // Whenever Restless Vents attacks, you may discard a card. If you do, draw a card.
        this.addAbility(new AttacksTriggeredAbility(
                new DoIfCostPaid(
                        new DrawCardSourceControllerEffect(1),
                        new DiscardCardCost()
                )
        ));
    }

    private RestlessVents(final RestlessVents card) {
        super(card);
    }

    @Override
    public RestlessVents copy() {
        return new RestlessVents(this);
    }
}

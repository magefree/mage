package mage.cards.r;

import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.mana.BlueManaAbility;
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
public final class RestlessSpire extends CardImpl {

    public RestlessSpire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Restless Spire enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {U} or {R}.
        this.addAbility(new BlueManaAbility());
        this.addAbility(new RedManaAbility());

        // {U}{R}: Until end of turn, Restless Spire becomes a 2/1 blue and red Elemental creature with "As long as it's your turn, this creature has first strike". It's still a land.
        this.addAbility(new SimpleActivatedAbility(new BecomesCreatureSourceEffect(
                new CreatureToken(2, 1, "2/1 blue and red Elemental creature with \"As long as it's your turn, this creature has first strike.\"")
                        .withColor("UR").withSubType(SubType.ELEMENTAL)
                        .withAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                                new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield),
                                MyTurnCondition.instance, "As long as it's your turn, this creature has first strike."
                        )).addHint(MyTurnHint.instance)),
                CardType.LAND, Duration.EndOfTurn, true
        ), new ManaCostsImpl<>("{U}{R}")));

        // Whenever Restless Spire attacks, scry 1.
        this.addAbility(new AttacksTriggeredAbility(new ScryEffect(1, false), false));
    }

    private RestlessSpire(final RestlessSpire card) {
        super(card);
    }

    @Override
    public RestlessSpire copy() {
        return new RestlessSpire(this);
    }
}

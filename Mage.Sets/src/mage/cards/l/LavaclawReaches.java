package mage.cards.l;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.game.permanent.token.custom.CreatureToken;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class LavaclawReaches extends CardImpl {

    public LavaclawReaches(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},null);

        // Lavaclaw Reaches enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {B} or {R}.
        this.addAbility(new BlackManaAbility());
        this.addAbility(new RedManaAbility());

        // {1}{B}{R}: Until end of turn, Lavaclaw Reaches becomes a 2/2 black and red Elemental creature with ": This creature gets +X/+0 until end of turn." It's still a land.
        this.addAbility(new SimpleActivatedAbility(
            new BecomesCreatureSourceEffect(
                new CreatureToken(2, 2, "2/2 black and red Elemental creature with \"{X}: This creature gets +X/+0 until end of turn.\"", SubType.ELEMENTAL)
                    .withColor("BR")
                    .withAbility(
                        new SimpleActivatedAbility(new BoostSourceEffect(GetXValue.instance, StaticValue.get(0), Duration.EndOfTurn),
                        new ManaCostsImpl<>("{X}"))
                    ),
                CardType.LAND,
                Duration.EndOfTurn
            ).withDurationRuleAtStart(true),
            new ManaCostsImpl<>("{1}{B}{R}")
        ));
    }

    private LavaclawReaches(final LavaclawReaches card) {
        super(card);
    }

    @Override
    public LavaclawReaches copy() {
        return new LavaclawReaches(this);
    }
}

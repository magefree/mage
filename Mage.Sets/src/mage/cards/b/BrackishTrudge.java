package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrackishTrudge extends CardImpl {

    private static final Condition condition = new YouGainedLifeCondition(ComparisonType.MORE_THAN, 0);
    private static final Hint hint = new ConditionHint(condition, "You gained life this turn");

    public BrackishTrudge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.FUNGUS);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Brackish Trudge enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {1}{B}: Return Brackish Trudge from your graveyard to your hand. Activate only if you gained life this turn.
        this.addAbility(new ActivateIfConditionActivatedAbility(
                Zone.GRAVEYARD, new ReturnSourceFromGraveyardToHandEffect(),
                new ManaCostsImpl<>("{1}{B}"), condition
        ).addHint(hint), new PlayerGainedLifeWatcher());
    }

    private BrackishTrudge(final BrackishTrudge card) {
        super(card);
    }

    @Override
    public BrackishTrudge copy() {
        return new BrackishTrudge(this);
    }
}

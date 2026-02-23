package mage.cards.n;

import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.permanent.token.custom.CreatureToken;

import java.util.UUID;

/**
 * @author anonymous
 */
public final class NantukoMonastery extends CardImpl {

    public NantukoMonastery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // Threshold - {G}{W}: Nantuko Monastery becomes a 4/4 green and white Insect Monk creature with first strike until end of turn. It's still a land. Activate this ability only if seven or more cards are in your graveyard.
        this.addAbility(new ActivateIfConditionActivatedAbility(
            new BecomesCreatureSourceEffect(
                new CreatureToken(
                    4, 4, "4/4 green and white Insect Monk creature with first strike", SubType.INSECT, SubType.MONK
                ).withColor("GW").withAbility(FirstStrikeAbility.getInstance()),
                CardType.LAND,
                Duration.EndOfTurn
            ),
            new ManaCostsImpl<>("{G}{W}"),
            ThresholdCondition.instance
        ).setAbilityWord(AbilityWord.THRESHOLD));
    }

    private NantukoMonastery(final NantukoMonastery card) {
        super(card);
    }

    @Override
    public NantukoMonastery copy() {
        return new NantukoMonastery(this);
    }
}

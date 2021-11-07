package mage.cards.h;

import mage.MageInt;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.permanent.token.SpiritWhiteToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HeronBlessedGeist extends CardImpl {

    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(StaticFilters.FILTER_ENCHANTMENT_PERMANENT);
    private static final Hint hint = new ConditionHint(condition, "You control an enchantment");

    public HeronBlessedGeist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {3}{W}, Exile Heron-Blessed Geist from your graveyard: Create two 1/1 white Spirit creature tokens with flying. Activate only if you control an enchantment and only as a sorcery.
        ActivatedAbility ability = new ActivateIfConditionActivatedAbility(
                Zone.GRAVEYARD, new CreateTokenEffect(new SpiritWhiteToken(), 2),
                new ManaCostsImpl<>("{3}{W}"), condition
        );
        ability.setTiming(TimingRule.SORCERY);
        this.addAbility(ability.addHint(hint));
    }

    private HeronBlessedGeist(final HeronBlessedGeist card) {
        super(card);
    }

    @Override
    public HeronBlessedGeist copy() {
        return new HeronBlessedGeist(this);
    }
}

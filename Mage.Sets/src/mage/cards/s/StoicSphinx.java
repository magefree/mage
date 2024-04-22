package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.HaventCastSpellThisTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class StoicSphinx extends CardImpl {

    public StoicSphinx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.subtype.add(SubType.SPHINX);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Stoic Sphinx has hexproof as long as you haven't cast a spell this turn.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(HexproofAbility.getInstance(), Duration.WhileOnBattlefield),
                HaventCastSpellThisTurnCondition.instance,
                "{this} has hexproof as long as you haven't cast a spell this turn"
        )));
    }

    private StoicSphinx(final StoicSphinx card) {
        super(card);
    }

    @Override
    public StoicSphinx copy() {
        return new StoicSphinx(this);
    }
}

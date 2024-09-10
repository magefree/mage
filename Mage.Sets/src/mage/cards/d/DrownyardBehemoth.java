
package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceEnteredThisTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.EmergeAbility;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class DrownyardBehemoth extends CardImpl {

    public DrownyardBehemoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{9}");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.CRAB);
        this.power = new MageInt(5);
        this.toughness = new MageInt(7);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // Emerge {7}{U}
        this.addAbility(new EmergeAbility(this, "{7}{U}"));
        
        // Drownyard Behemoth has hexproof as long as it entered the battlefield this turn.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(HexproofAbility.getInstance(), Duration.WhileOnBattlefield),
                SourceEnteredThisTurnCondition.instance, "{this} has hexproof as long as it entered the battlefield this turn"
        )));
    }

    private DrownyardBehemoth(final DrownyardBehemoth card) {
        super(card);
    }

    @Override
    public DrownyardBehemoth copy() {
        return new DrownyardBehemoth(this);
    }
}

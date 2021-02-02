
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.HellbentCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.ShadowAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LoneFox
 */
public final class CutthroatIlDal extends CardImpl {

    public CutthroatIlDal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // Hellbent - Cutthroat il-Dal has shadow as long as you have no cards in hand.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
            new GainAbilitySourceEffect(ShadowAbility.getInstance(), Duration.WhileOnBattlefield),                                                                                                            HellbentCondition.instance,
            "<i>Hellbent</i> &mdash; {this} has shadow as long as you have no cards in hand")));                                                                                                   }

    private CutthroatIlDal(final CutthroatIlDal card) {
        super(card);
    }

    @Override
    public CutthroatIlDal copy() {
        return new CutthroatIlDal(this);
    }
}

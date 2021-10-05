
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class HeronsGraceChampion extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("other Humans");

    static {
        filter.add(SubType.HUMAN.getPredicate());
    }

    public HeronsGraceChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // When Heron's Grace Champion enters the battlefield, other Humans you control get +1/+1 and gain lifelink until end of turn.
        Effect effect = new BoostControlledEffect(1, 1, Duration.EndOfTurn, filter, true);
        effect.setText("other Humans you control get +1/+1");
        Ability ability = new EntersBattlefieldTriggeredAbility(effect);
        effect = new GainAbilityControlledEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn, filter, true);
        effect.setText("and gain lifelink until end of turn");
        ability.addEffect(effect);
        this.addAbility(ability);        
    }

    private HeronsGraceChampion(final HeronsGraceChampion card) {
        super(card);
    }

    @Override
    public HeronsGraceChampion copy() {
        return new HeronsGraceChampion(this);
    }
}

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IntimidateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class ShadowAlleyDenizen extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another black creature");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public ShadowAlleyDenizen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever another black creature enters the battlefield under your control, target creature gains intimidate until end of turn.
        Effect effect = new GainAbilityTargetEffect(IntimidateAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("target creature gains intimidate until end of turn. <i>(It can't be blocked except by artifact creatures and/or creatures that share a color with it.)</i>");
        Ability ability = new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, effect, filter, false, null, true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ShadowAlleyDenizen(final ShadowAlleyDenizen card) {
        super(card);
    }

    @Override
    public ShadowAlleyDenizen copy() {
        return new ShadowAlleyDenizen(this);
    }
}

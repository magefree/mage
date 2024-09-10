
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.HeroicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class AkroanConscriptor extends CardImpl {

    public AkroanConscriptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // <i>Heroic</i> &mdash; Whenever you cast a spell that targets Akroan Conscriptor, gain control of another target creature until end of turn. Untap that creature. It gains haste until end of turn.
        Ability ability = new HeroicAbility(new GainControlTargetEffect(Duration.EndOfTurn, true), false);
        Effect effect = new UntapTargetEffect();
        effect.setText("Untap that creature");
        ability.addEffect(effect);
        effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("It gains haste until end of turn");
        ability.addEffect(effect);
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE));
        this.addAbility(ability);

    }

    private AkroanConscriptor(final AkroanConscriptor card) {
        super(card);
    }

    @Override
    public AkroanConscriptor copy() {
        return new AkroanConscriptor(this);
    }
}


package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class VoraciousVampire extends CardImpl {

    public VoraciousVampire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // When Voracious Vampire enters the battlefield, target Vampire you control gets +1/+1 and gains menace until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(1, 1, Duration.EndOfTurn), false);
        Effect effect = new GainAbilityTargetEffect(new MenaceAbility(), Duration.EndOfTurn);
        effect.setText("and gains menace until end of turn.");
        ability.addEffect(effect);
        ability.addTarget(new TargetControlledCreaturePermanent(new FilterControlledCreaturePermanent(SubType.VAMPIRE, "Vampire you control")));
        this.addAbility(ability);
    }

    private VoraciousVampire(final VoraciousVampire card) {
        super(card);
    }

    @Override
    public VoraciousVampire copy() {
        return new VoraciousVampire(this);
    }
}

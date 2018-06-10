
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetAnyTarget;
import mage.watchers.common.PlayerAttackedWatcher;

/**
 *
 * @author TheElk801
 */
public final class RepeatingBarrage extends CardImpl {

    public RepeatingBarrage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}{R}");

        // Repeating Barrage deals 3 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetAnyTarget());

        // Raid — {3}{R}{R}: Return Repeating Barrage from your graveyard to your hand. Activate this ability only if you attacked with a creature this turn.
        Ability ability = new ConditionalActivatedAbility(Zone.GRAVEYARD,
                new ReturnSourceFromGraveyardToHandEffect(),
                new ManaCostsImpl("{3}{R}{R}"),
                RaidCondition.instance);
        ability.setAbilityWord(AbilityWord.RAID);
        this.addAbility(ability, new PlayerAttackedWatcher());
    }

    public RepeatingBarrage(final RepeatingBarrage card) {
        super(card);
    }

    @Override
    public RepeatingBarrage copy() {
        return new RepeatingBarrage(this);
    }
}

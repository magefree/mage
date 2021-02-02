
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.RemoveFromCombatSourceEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class GustcloakSentinel extends CardImpl {

    public GustcloakSentinel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Gustcloak Sentinel becomes blocked, you may untap it and remove it from combat.
        Ability ability = new BecomesBlockedSourceTriggeredAbility(new UntapSourceEffect(), true);
        Effect effect = new RemoveFromCombatSourceEffect();
        effect.setText("and remove it from combat");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private GustcloakSentinel(final GustcloakSentinel card) {
        super(card);
    }

    @Override
    public GustcloakSentinel copy() {
        return new GustcloakSentinel(this);
    }
}

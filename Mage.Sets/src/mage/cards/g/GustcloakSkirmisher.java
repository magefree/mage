
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.RemoveFromCombatSourceEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class GustcloakSkirmisher extends CardImpl {

    public GustcloakSkirmisher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Whenever Gustcloak Skirmisher becomes blocked, you may untap it and remove it from combat.
        Ability ability = new BecomesBlockedSourceTriggeredAbility(new UntapSourceEffect(), true);
        Effect effect = new RemoveFromCombatSourceEffect();
        effect.setText("and remove it from combat");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private GustcloakSkirmisher(final GustcloakSkirmisher card) {
        super(card);
    }

    @Override
    public GustcloakSkirmisher copy() {
        return new GustcloakSkirmisher(this);
    }
}

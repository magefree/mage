
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ExertCreatureControllerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.keyword.ExertAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author spjspj
 */
public final class ResoluteSurvivors extends CardImpl {

    public ResoluteSurvivors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // You may exert Resolute Survivors as it attacks.
        this.addAbility(new ExertAbility(null, false));

        // Whenever you exert a creature, Resolute Survivors deals 1 damage to each opponent and you gain 1 life.
        Effect effect = new LoseLifeOpponentsEffect(1);
        effect.setText("Whenever you exert a creature, {this} deals 1 damage to each opponent");
        Ability ability = new ExertCreatureControllerTriggeredAbility(effect);
        effect = new GainLifeEffect(1);
        effect.setText("and you gain 1 life");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private ResoluteSurvivors(final ResoluteSurvivors card) {
        super(card);
    }

    @Override
    public ResoluteSurvivors copy() {
        return new ResoluteSurvivors(this);
    }
}

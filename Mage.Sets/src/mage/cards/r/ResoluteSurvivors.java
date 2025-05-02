package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ExertCreatureControllerTriggeredAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.ExertAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

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
        Ability ability = new ExertCreatureControllerTriggeredAbility(new DamagePlayersEffect(1, TargetController.OPPONENT));
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
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

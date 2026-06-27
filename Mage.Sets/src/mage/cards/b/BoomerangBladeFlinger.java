package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class BoomerangBladeFlinger extends CardImpl {

    public BoomerangBladeFlinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever Boomerang attacks, he deals 1 damage to each opponent and you gain 1 life.
        Ability ability = new AttacksTriggeredAbility(new DamagePlayersEffect(1, TargetController.OPPONENT, "he"), false);
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private BoomerangBladeFlinger(final BoomerangBladeFlinger card) {
        super(card);
    }

    @Override
    public BoomerangBladeFlinger copy() {
        return new BoomerangBladeFlinger(this);
    }
}

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class ScreechingSoulbreaker extends CardImpl {

    public ScreechingSoulbreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.SIREN);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever this creature attacks, it deals 1 damage to each opponent and you gain 1 life.
        Ability ability = new AttacksTriggeredAbility(new DamagePlayersEffect(
            1, TargetController.OPPONENT, "it"
        ));
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private ScreechingSoulbreaker(final ScreechingSoulbreaker card) {
        super(card);
    }

    @Override
    public ScreechingSoulbreaker copy() {
        return new ScreechingSoulbreaker(this);
    }
}

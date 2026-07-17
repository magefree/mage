package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.discard.DiscardAndDrawThatManyEffect;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class ColossusOfTheBloodAge extends CardImpl {

    public ColossusOfTheBloodAge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{R}{W}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // When this creature enters, it deals 3 damage to each opponent and you gain 3 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(
            new DamagePlayersEffect(3, TargetController.OPPONENT, "it")
        );
        ability.addEffect(new GainLifeEffect(3).concatBy("and"));
        this.addAbility(ability);

        // When this creature dies, discard any number of cards, then draw that many cards plus one.
        this.addAbility(new DiesSourceTriggeredAbility(new DiscardAndDrawThatManyEffect(Integer.MAX_VALUE, 1)));
    }

    private ColossusOfTheBloodAge(final ColossusOfTheBloodAge card) {
        super(card);
    }

    @Override
    public ColossusOfTheBloodAge copy() {
        return new ColossusOfTheBloodAge(this);
    }
}

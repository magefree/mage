package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class ProfessorHulk extends CardImpl {

    public ProfessorHulk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GAMMA);
        this.subtype.add(SubType.SCIENTIST);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Professor Hulk deals combat damage to a player, draw that many cards.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
            new DrawCardSourceControllerEffect(SavedDamageValue.MANY)
        ));
    }

    private ProfessorHulk(final ProfessorHulk card) {
        super(card);
    }

    @Override
    public ProfessorHulk copy() {
        return new ProfessorHulk(this);
    }
}

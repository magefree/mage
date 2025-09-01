package mage.cards.j;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.ExileAndReturnSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JechtReluctantGuardian extends CardImpl {

    public JechtReluctantGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);
        this.secondSideCardClazz = mage.cards.b.BraskasFinalAeon.class;

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever Jecht deals combat damage to a player, you may exile it, then return it to the battlefield transformed under its owner's control.
        this.addAbility(new TransformAbility());
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new ExileAndReturnSourceEffect(PutCards.BATTLEFIELD_TRANSFORMED)
                        .setText("exile it, then return it to the battlefield transformed under its owner's control"), true
        ));
    }

    private JechtReluctantGuardian(final JechtReluctantGuardian card) {
        super(card);
    }

    @Override
    public JechtReluctantGuardian copy() {
        return new JechtReluctantGuardian(this);
    }
}

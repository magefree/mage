package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.PreventAllDamageToSourceEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class KingTChalla extends ModalDoubleFacedCard {

    public KingTChalla(UUID ownerId, CardSetInfo setInfo) {
        super(
            ownerId, setInfo,
            // King T'Challa
            new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.NOBLE, SubType.HERO}, "{1}{W}{U}",
            "Black Panther, Hope Enduring",
            new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WARRIOR, SubType.HERO}, "{4}{W}{U}"
        );

        // 1.
        // King T'Challa
        // Legendary Creature — Human Noble Hero
        this.getLeftHalfCard().setPT(new MageInt(3), new MageInt(2));

        // Flash
        this.getLeftHalfCard().addAbility(FlashAbility.getInstance());

        // Whenever a player draws their second card each turn, you draw a card.
        this.getLeftHalfCard().addAbility(new DrawNthCardTriggeredAbility(
            new DrawCardSourceControllerEffect(1),
            false, TargetController.ANY, 2
        ));

        // {4}{W}{U}: Transform King T'Challa. Activate only as a sorcery.
        this.getLeftHalfCard().addAbility(new ActivateAsSorceryActivatedAbility(new TransformSourceEffect(), new ManaCostsImpl<>("{4}{W}{U}")));

        // 2.
        // Black Panther, Hope Enduring
        // Legendary Creature — Human Warrior Hero
        this.getRightHalfCard().setPT(new MageInt(3), new MageInt(3));

        // Flash
        this.getRightHalfCard().addAbility(FlashAbility.getInstance());

        // Double strike
        this.getRightHalfCard().addAbility(DoubleStrikeAbility.getInstance());

        // Prevent all damage that would be dealt to Black Panther.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(
            new PreventAllDamageToSourceEffect(Duration.WhileOnBattlefield)
                .setText("Prevent all damage that would be dealt to {this}")
        ));

        // Whenever Black Panther deals combat damage to a player, draw a card.
        this.getRightHalfCard().addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
            new DrawCardSourceControllerEffect(1)
        ));
    }

    private KingTChalla(final KingTChalla card) {
        super(card);
    }

    @Override
    public KingTChalla copy() {
        return new KingTChalla(this);
    }
}

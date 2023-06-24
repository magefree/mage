package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.BlocksOrBlockedByCreatureSourceTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BecomesColorTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

/**
 *
 * @author djbrez
 */
public final class AislingLeprechaun extends CardImpl {

    public AislingLeprechaun(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");
        this.subtype.add(SubType.FAERIE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Aisling Leprechaun blocks or becomes blocked by a creature, that creature becomes green.
        Effect effect = new BecomesColorTargetEffect(ObjectColor.GREEN, Duration.EndOfGame);
        effect.setText("that creature becomes green");
        this.addAbility(new BlocksOrBlockedByCreatureSourceTriggeredAbility(effect));
    }

    private AislingLeprechaun(final AislingLeprechaun card) {
        super(card);
    }

    @Override
    public AislingLeprechaun copy() {
        return new AislingLeprechaun(this);
    }
}

package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NecronDeathmark extends CardImpl {

    public NecronDeathmark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.NECRON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Synaptic Disintegrator -- When Necron Deathmark enters the battlefield, destroy up to one target creature and target player mills three cards.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect());
        ability.addEffect(new MillCardsTargetEffect(3)
                .setText("and target player mills three cards")
                .setTargetPointer(new SecondTargetPointer()));
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability.withFlavorWord("Synaptic Disintegrator"));
    }

    private NecronDeathmark(final NecronDeathmark card) {
        super(card);
    }

    @Override
    public NecronDeathmark copy() {
        return new NecronDeathmark(this);
    }
}

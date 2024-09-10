package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RimrockKnight extends AdventureCard {

    public RimrockKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.INSTANT}, "{1}{R}", "Boulder Rush", "{R}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Rimrock Knight can't block
        this.addAbility(new CantBlockAbility());

        // Boulder Rush
        // Target creature gets +2/+0 until end of turn.
        this.getSpellCard().getSpellAbility().addEffect(new BoostTargetEffect(2, 0, Duration.EndOfTurn));
        this.getSpellCard().getSpellAbility().addTarget(new TargetCreaturePermanent());

        this.finalizeAdventure();
    }

    private RimrockKnight(final RimrockKnight card) {
        super(card);
    }

    @Override
    public RimrockKnight copy() {
        return new RimrockKnight(this);
    }
}

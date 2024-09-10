package mage.cards.s;

import mage.MageInt;
import mage.abilities.effects.common.UntapTargetEffect;
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
public final class SilverflameSquire extends AdventureCard {

    public SilverflameSquire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.INSTANT}, "{1}{W}", "On Alert", "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // On Alert
        // Target creature gets +2/+2 until end of turn. Untap it.
        this.getSpellCard().getSpellAbility().addEffect(new BoostTargetEffect(2, 2, Duration.EndOfTurn));
        this.getSpellCard().getSpellAbility().addEffect(new UntapTargetEffect().setText("Untap it"));
        this.getSpellCard().getSpellAbility().addTarget(new TargetCreaturePermanent());

        this.finalizeAdventure();
    }

    private SilverflameSquire(final SilverflameSquire card) {
        super(card);
    }

    @Override
    public SilverflameSquire copy() {
        return new SilverflameSquire(this);
    }
}

package mage.cards.a;

import mage.MageInt;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArdenvaleTactician extends AdventureCard {

    public ArdenvaleTactician(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.INSTANT}, "{1}{W}{W}", "Dizzying Swoop", "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Dizzying Swoop
        // Tap up to two target creatures.
        this.getSpellCard().getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellCard().getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));

        this.finalizeAdventure();
    }

    private ArdenvaleTactician(final ArdenvaleTactician card) {
        super(card);
    }

    @Override
    public ArdenvaleTactician copy() {
        return new ArdenvaleTactician(this);
    }
}

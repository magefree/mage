package mage.cards.o;

import mage.MageInt;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
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
public final class ObyrasAttendants extends AdventureCard {

    public ObyrasAttendants(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.INSTANT}, "{4}{U}", "Desperate Parry", "{1}{U}");

        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Desperate Parry
        // Target creature gets -4/-0 until end of turn.
        this.getSpellCard().getSpellAbility().addEffect(new BoostTargetEffect(-4, 0));
        this.getSpellCard().getSpellAbility().addTarget(new TargetCreaturePermanent());

        this.finalizeAdventure();
    }

    private ObyrasAttendants(final ObyrasAttendants card) {
        super(card);
    }

    @Override
    public ObyrasAttendants copy() {
        return new ObyrasAttendants(this);
    }
}

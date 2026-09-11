package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.game.permanent.token.CadetToken;
import mage.abilities.common.EntersPreparedAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class FateholdChronologist extends PrepareCard {

    public FateholdChronologist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U/W}", "Peer Review", new CardType[]{CardType.SORCERY}, "{2}{U/W}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // This creature enters prepared.
        this.addAbility(new EntersPreparedAbility());

        // Peer Review
        // Sorcery {2}{U/W}
        // Create a 2/2 colorless Wizard Soldier creature token named Cadet. Surveil 1.
        this.getSpellCard().getSpellAbility().addEffect(new CreateTokenEffect(new CadetToken()));
        this.getSpellCard().getSpellAbility().addEffect(new SurveilEffect(1));
    }

    private FateholdChronologist(final FateholdChronologist card) {
        super(card);
    }

    @Override
    public FateholdChronologist copy() {
        return new FateholdChronologist(this);
    }
}

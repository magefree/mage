package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersPreparedAbility;
import mage.abilities.common.ScryOrSurveilTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.constants.SubType;
import mage.game.permanent.token.CadetToken;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class PrudentFateseer extends PrepareCard {

    public PrudentFateseer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W/U}{W/U}", "Peer Review", new CardType[]{CardType.SORCERY}, "{2}{W/U}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // This creature enters prepared.
        this.addAbility(new EntersPreparedAbility());

        // Whenever you scry or surveil, creatures you control get +1/+0 until end of turn. This ability triggers only once each turn.
        this.addAbility(new ScryOrSurveilTriggeredAbility(new BoostControlledEffect(1, 0, Duration.EndOfTurn))
            .setTriggersLimitEachTurn(1));

        // Peer Review
        // Sorcery {2}{W/U}
        // Create a 2/2 colorless Wizard Soldier creature token named Cadet. Surveil 1.
        this.getSpellCard().getSpellAbility().addEffect(new CreateTokenEffect(new CadetToken()));
        this.getSpellCard().getSpellAbility().addEffect(new SurveilEffect(1));
    }

    private PrudentFateseer(final PrudentFateseer card) {
        super(card);
    }

    @Override
    public PrudentFateseer copy() {
        return new PrudentFateseer(this);
    }
}

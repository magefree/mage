package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.EntersPreparedAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.constants.SubType;
import mage.game.permanent.token.CadetToken;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class SemesterForeseer extends PrepareCard {

    public SemesterForeseer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}", "Peer Review", new CardType[]{CardType.SORCERY}, "{2}{W/U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // This creature enters prepared.
        this.addAbility(new EntersPreparedAbility());

        // When this creature enters, surveil 1.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SurveilEffect(1)));

        // Peer Review
        // Sorcery {2}{W/U}
        // Create a 2/2 colorless Wizard Soldier creature token named Cadet. Surveil 1.
        this.getSpellCard().getSpellAbility().addEffect(new CreateTokenEffect(new CadetToken()));
        this.getSpellCard().getSpellAbility().addEffect(new SurveilEffect(1));
    }

    private SemesterForeseer(final SemesterForeseer card) {
        super(card);
    }

    @Override
    public SemesterForeseer copy() {
        return new SemesterForeseer(this);
    }
}

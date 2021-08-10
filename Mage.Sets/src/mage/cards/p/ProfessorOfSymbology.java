package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LearnEffect;
import mage.abilities.hint.common.OpenSideboardHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ProfessorOfSymbology extends CardImpl {

    public ProfessorOfSymbology(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Professor of Symbology enters the battlefield, learn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LearnEffect())
                .addHint(OpenSideboardHint.instance));
    }

    private ProfessorOfSymbology(final ProfessorOfSymbology card) {
        super(card);
    }

    @Override
    public ProfessorOfSymbology copy() {
        return new ProfessorOfSymbology(this);
    }
}

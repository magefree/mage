package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LearnEffect;
import mage.abilities.hint.common.OpenSideboardHint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GnarledProfessor extends CardImpl {

    public GnarledProfessor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Gnarled Professor enters the battlefield, learn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LearnEffect())
                .addHint(OpenSideboardHint.instance));
    }

    private GnarledProfessor(final GnarledProfessor card) {
        super(card);
    }

    @Override
    public GnarledProfessor copy() {
        return new GnarledProfessor(this);
    }
}

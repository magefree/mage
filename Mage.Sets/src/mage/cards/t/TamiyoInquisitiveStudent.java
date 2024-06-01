package mage.cards.t;

import mage.MageInt;
import mage.abilities.Pronoun;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.effects.common.ExileAndReturnSourceEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TamiyoInquisitiveStudent extends CardImpl {

    public TamiyoInquisitiveStudent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MOONFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        this.secondSideCardClazz = TamiyoSeasonedScholar.class;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Tamiyo, Inquisitive Student attacks, investigate.
        this.addAbility(new AttacksTriggeredAbility(new InvestigateEffect()));

        // When you draw your third card in a turn, exile Tamiyo, then return her to the battlefield transformed under her owner's control.
        this.addAbility(new TransformAbility());
        this.addAbility(new DrawNthCardTriggeredAbility(
                new ExileAndReturnSourceEffect(PutCards.BATTLEFIELD_TRANSFORMED, Pronoun.SHE),
                false, 3
        ).setTriggerPhrase("When you draw your third card in a turn, "));
    }

    private TamiyoInquisitiveStudent(final TamiyoInquisitiveStudent card) {
        super(card);
    }

    @Override
    public TamiyoInquisitiveStudent copy() {
        return new TamiyoInquisitiveStudent(this);
    }
}

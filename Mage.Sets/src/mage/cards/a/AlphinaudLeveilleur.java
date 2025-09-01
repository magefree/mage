package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.CastSecondSpellTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.PartnerWithAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AlphinaudLeveilleur extends CardImpl {

    public AlphinaudLeveilleur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Partner with Alisaie Leveilleur
        this.addAbility(new PartnerWithAbility("Alisaie Leveilleur"));

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Eukrasia -- Whenever you cast your second spell each turn, draw a card.
        this.addAbility(new CastSecondSpellTriggeredAbility(new DrawCardSourceControllerEffect(1)).withFlavorWord("Eukrasia"));
    }

    private AlphinaudLeveilleur(final AlphinaudLeveilleur card) {
        super(card);
    }

    @Override
    public AlphinaudLeveilleur copy() {
        return new AlphinaudLeveilleur(this);
    }
}

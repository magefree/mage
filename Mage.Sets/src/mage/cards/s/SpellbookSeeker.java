package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersPreparedAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpellbookSeeker extends PrepareCard {

    public SpellbookSeeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}", "Careful Study", CardType.SORCERY, "{U}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // This creature enters prepared.
        this.addAbility(new EntersPreparedAbility());

        // Careful Study
        // Sorcery {U}
        // Draw two cards, then discard two cards.
        this.getSpellCard().getSpellAbility().addEffect(new DrawDiscardControllerEffect(2, 1));
    }

    private SpellbookSeeker(final SpellbookSeeker card) {
        super(card);
    }

    @Override
    public SpellbookSeeker copy() {
        return new SpellbookSeeker(this);
    }
}

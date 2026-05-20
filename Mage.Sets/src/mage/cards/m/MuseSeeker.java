package mage.cards.m;

import mage.MageInt;
import mage.abilities.abilityword.OpusAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MuseSeeker extends CardImpl {

    public MuseSeeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Opus -- Whenever you cast an instant or sorcery spell, draw a card. Then discard a card unless five or more mana was spent to cast that spell.
        this.addAbility(new OpusAbility(
                new DrawDiscardControllerEffect(1, 1),
                new DrawCardSourceControllerEffect(1), "draw a card. " +
                "Then discard a card unless five or more mana was spent to cast that spell", true
        ));
    }

    private MuseSeeker(final MuseSeeker card) {
        super(card);
    }

    @Override
    public MuseSeeker copy() {
        return new MuseSeeker(this);
    }
}

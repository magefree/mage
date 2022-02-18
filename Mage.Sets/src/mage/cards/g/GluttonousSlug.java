package mage.cards.g;

import mage.MageInt;
import mage.abilities.keyword.EvolveAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GluttonousSlug extends CardImpl {

    public GluttonousSlug(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.SLUG);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Evolve
        this.addAbility(new EvolveAbility());
    }

    private GluttonousSlug(final GluttonousSlug card) {
        super(card);
    }

    @Override
    public GluttonousSlug copy() {
        return new GluttonousSlug(this);
    }
}

package mage.cards.l;

import mage.MageInt;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.KnightToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LonesomeUnicorn extends AdventureCard {

    public LonesomeUnicorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{4}{W}", "Rider in Need", "{2}{W}");

        this.subtype.add(SubType.UNICORN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Rider in Need
        // Create a 2/2 white Knight creature token with vigilance.
        this.getSpellCard().getSpellAbility().addEffect(new CreateTokenEffect(new KnightToken()));

        this.finalizeAdventure();
    }

    private LonesomeUnicorn(final LonesomeUnicorn card) {
        super(card);
    }

    @Override
    public LonesomeUnicorn copy() {
        return new LonesomeUnicorn(this);
    }
}

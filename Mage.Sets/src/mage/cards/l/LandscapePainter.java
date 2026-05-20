package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.EntersPreparedAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LandscapePainter extends PrepareCard {

    public LandscapePainter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}", "Vibrant Idea", new CardType[]{CardType.SORCERY}, "{4}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // This creature enters prepared.
        this.addAbility(new EntersPreparedAbility());

        // Vibrant Idea
        // Sorcery {4}{U}
        // Draw two cards.
        this.getSpellCard().getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
    }

    private LandscapePainter(final LandscapePainter card) {
        super(card);
    }

    @Override
    public LandscapePainter copy() {
        return new LandscapePainter(this);
    }
}

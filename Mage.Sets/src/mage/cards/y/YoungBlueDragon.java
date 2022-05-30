package mage.cards.y;

import mage.MageInt;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YoungBlueDragon extends AdventureCard {

    public YoungBlueDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{4}{U}", "Sand Augury", "{1}{U}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Sand Augury
        // Scry 1, then draw a card.
        this.getSpellCard().getSpellAbility().addEffect(new ScryEffect(1, false));
        this.getSpellCard().getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy(", then"));
    }

    private YoungBlueDragon(final YoungBlueDragon card) {
        super(card);
    }

    @Override
    public YoungBlueDragon copy() {
        return new YoungBlueDragon(this);
    }
}

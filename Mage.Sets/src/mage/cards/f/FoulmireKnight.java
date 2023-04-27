package mage.cards.f;

import mage.MageInt;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FoulmireKnight extends AdventureCard {

    public FoulmireKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.INSTANT}, "{B}", "Profane Insight", "{2}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Profane Insight
        // You draw a card and you lose 1 life.
        this.getSpellCard().getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).setText("You draw a card"));
        this.getSpellCard().getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));
    }

    private FoulmireKnight(final FoulmireKnight card) {
        super(card);
    }

    @Override
    public FoulmireKnight copy() {
        return new FoulmireKnight(this);
    }
}

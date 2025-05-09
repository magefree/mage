package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.asthought.MayCastFromGraveyardAsAdventureEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class MosswoodDreadknight extends AdventureCard {


    public MosswoodDreadknight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{1}{G}", "Dread Whispers", "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Mosswood Dreadknight dies, you may cast it from your graveyard as an Adventure until the end of your next turn.
        this.addAbility(new DiesSourceTriggeredAbility(new MayCastFromGraveyardAsAdventureEffect()));

        // Dread Whispers
        // You draw a card and you lose 1 life.
        this.getSpellCard().getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1, true));
        this.getSpellCard().getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));

        this.finalizeAdventure();
    }

    private MosswoodDreadknight(final MosswoodDreadknight card) {
        super(card);
    }

    @Override
    public MosswoodDreadknight copy() {
        return new MosswoodDreadknight(this);
    }
}
package mage.cards.u;

import mage.MageInt;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ChooseCardTypeEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionAllOfChosenCardTypeEffect;
import mage.abilities.keyword.CompanionAbility;
import mage.abilities.keyword.CompanionCondition;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author emerald000
 */
public final class UmoriTheCollector extends CardImpl {

    private static final FilterCard filter = new FilterCard("Spells you cast of the chosen type");

    public UmoriTheCollector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B/G}{B/G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.OOZE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Companion — Each nonland card in your starting deck shares a card type.
        this.addAbility(new CompanionAbility(UmoriCondition.instance));

        // As Umori, the Collector enters the battlefield, choose a card type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCardTypeEffect(Outcome.Benefit)));

        // Spells you cast of the chosen type cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionAllOfChosenCardTypeEffect(filter, 1, true)));
    }

    private UmoriTheCollector(final UmoriTheCollector card) {
        super(card);
    }

    @Override
    public UmoriTheCollector copy() {
        return new UmoriTheCollector(this);
    }
}

enum UmoriCondition implements CompanionCondition {
    instance;

    @Override
    public String getRule() {
        return "Each nonland card in your starting deck shares a card type.";
    }

    @Override
    public boolean isLegal(Set<Card> deck, int startingHandSize) {
        Set<CardType> cardTypes = new HashSet<>();
        for (Card card : deck) {
            // Lands are fine.
            if (card.hasCardTypeForDeckbuilding(CardType.LAND)) {
                continue;
            }
            // First nonland checked.
            if (cardTypes.isEmpty()) {
                cardTypes.addAll(card.getCardTypeForDeckbuilding());
            } else {
                cardTypes.retainAll(card.getCardTypeForDeckbuilding());
                if (cardTypes.isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }
}
package mage.cards.u;

import mage.MageInt;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.DescendCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldWithCounterEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UchbenbakTheGreatMistake extends CardImpl {

    public UchbenbakTheGreatMistake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SKELETON);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Descend 8 -- {4}{U}{B}: Return Uchbenbak, the Great Mistake from your graveyard to the battlefield with a finality counter on it. Activate only if there are eight or more permanent cards in your graveyard and only as a sorcery.
        this.addAbility(new ActivateIfConditionActivatedAbility(
                Zone.GRAVEYARD, new ReturnSourceFromGraveyardToBattlefieldWithCounterEffect(CounterType.FINALITY.createInstance(), false),
                new ManaCostsImpl<>("{4}{U}{B}"), DescendCondition.EIGHT
        ).setTiming(TimingRule.SORCERY).setAbilityWord(AbilityWord.DESCEND_8).addHint(DescendCondition.getHint()));
    }

    private UchbenbakTheGreatMistake(final UchbenbakTheGreatMistake card) {
        super(card);
    }

    @Override
    public UchbenbakTheGreatMistake copy() {
        return new UchbenbakTheGreatMistake(this);
    }
}

package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.CorruptedCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FleshlessGladiator extends CardImpl {

    public FleshlessGladiator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.SKELETON);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Corrupted -- {2}{B}: Return Fleshless Gladiator from your graveyard to the battlefield tapped. You lose 1 life. Activate only if an opponent has three or more poison counters.
        Ability ability = new ConditionalActivatedAbility(
                Zone.GRAVEYARD, new ReturnSourceFromGraveyardToBattlefieldEffect(true),
                new ManaCostsImpl<>("{2}{B}"), CorruptedCondition.instance
        );
        ability.addEffect(new LoseLifeSourceControllerEffect(1));
        this.addAbility(ability.setAbilityWord(AbilityWord.CORRUPTED).addHint(CorruptedCondition.getHint()));
    }

    private FleshlessGladiator(final FleshlessGladiator card) {
        super(card);
    }

    @Override
    public FleshlessGladiator copy() {
        return new FleshlessGladiator(this);
    }
}

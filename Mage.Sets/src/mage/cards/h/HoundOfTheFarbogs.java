package mage.cards.h;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 * @author fireshoes
 */
public final class HoundOfTheFarbogs extends CardImpl {

    public HoundOfTheFarbogs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.DOG);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // <i>Delirium</i> &mdash; Hound of the Farborgs has menace as long as there are four or more card types among cards in your graveyard.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(new MenaceAbility(), Duration.WhileOnBattlefield),
                DeliriumCondition.instance,
                "{this} has menace as long as there are four or more card types among cards in your graveyard. " +
                        "<i>(A creature with menace can't be blocked except by two or more creatures.)</i>"));
        ability.setAbilityWord(AbilityWord.DELIRIUM);
        ability.addHint(CardTypesInGraveyardHint.YOU);
        this.addAbility(ability);
    }

    private HoundOfTheFarbogs(final HoundOfTheFarbogs card) {
        super(card);
    }

    @Override
    public HoundOfTheFarbogs copy() {
        return new HoundOfTheFarbogs(this);
    }
}

package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DescendCondition;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class WatertightGondola extends CardImpl {

    public WatertightGondola(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "");
        this.nightCard = true;
        this.color.setBlue(true);

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Descend 8 -- Watertight Gondola can't be blocked as long as there are eight or more permanent cards in your graveyard.
        Ability ability = new SimpleStaticAbility(new ConditionalRestrictionEffect(
                new CantBeBlockedSourceEffect(),
                DescendCondition.EIGHT
        ).setText("{this} can't be blocked as long as there are eight or more permanent cards in your graveyard"));
        this.addAbility(ability.addHint(DescendCondition.getHint()).setAbilityWord(AbilityWord.DESCEND_8));

        // Crew 1
        this.addAbility(new CrewAbility(1));

    }

    private WatertightGondola(final WatertightGondola card) {
        super(card);
    }

    @Override
    public WatertightGondola copy() {
        return new WatertightGondola(this);
    }
}

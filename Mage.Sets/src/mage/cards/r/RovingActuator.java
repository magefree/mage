package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.VoidCondition;
import mage.abilities.effects.common.ExileTargetCardCopyAndCastEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;
import mage.watchers.common.VoidWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RovingActuator extends CardImpl {

    private static final FilterCard filter
            = new FilterInstantOrSorceryCard("instant or sorcery card with mana value 2 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 3));
    }

    public RovingActuator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Void - When this creature enters, if a nonland permanent left the battlefield this turn or a spell was warped this turn, exile up to one target instant or sorcery card with mana value 2 or less from your graveyard. Copy it. You may cast the copy without paying its mana cost.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileTargetCardCopyAndCastEffect(true)
                .setText("exile up to one target instant or sorcery card with mana value 2 or less from your graveyard. " +
                        "Copy it. You may cast the copy without paying its mana cost")).withInterveningIf(VoidCondition.instance);
        ability.addTarget(new TargetCardInYourGraveyard(0, 1, filter));
        this.addAbility(ability.setAbilityWord(AbilityWord.VOID).addHint(VoidCondition.getHint()), new VoidWatcher());
    }

    private RovingActuator(final RovingActuator card) {
        super(card);
    }

    @Override
    public RovingActuator copy() {
        return new RovingActuator(this);
    }
}

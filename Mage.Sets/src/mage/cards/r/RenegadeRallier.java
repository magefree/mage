package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.RevoltCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;
import mage.watchers.common.RevoltWatcher;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class RenegadeRallier extends CardImpl {

    private static final FilterPermanentCard filter = new FilterPermanentCard("permanent card with mana value 2 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 3));
    }

    public RenegadeRallier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // <i>Revolt</i> &mdash; When Renegade Rallier enters the battlefield, if a permanent you controlled left the battlefield this turn,
        // return target permanent card with converted mana cost 2 or less from your graveyard to your battlefield.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect(), false),
                RevoltCondition.instance, "When {this} enters the battlefield, if a permanent you controlled " +
                "left the battlefield this turn, return target permanent card with mana value 2 or less from your graveyard to the battlefield."
        ).setAbilityWord(AbilityWord.REVOLT);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability, new RevoltWatcher());
    }

    private RenegadeRallier(final RenegadeRallier card) {
        super(card);
    }

    @Override
    public RenegadeRallier copy() {
        return new RenegadeRallier(this);
    }
}

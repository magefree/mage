package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CompletedDungeonCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotNonTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.keyword.VentureIntoTheDungeonEffect;
import mage.abilities.hint.common.CurrentDungeonHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;
import mage.watchers.common.CompletedDungeonWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BarrowinOfClanUndurr extends CardImpl {

    private static final FilterCard filter
            = new FilterCreatureCard("creature card with mana value 3 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public BarrowinOfClanUndurr(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Barrowin of Clan Undurr enters the battlefield, venture into the dungeon.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new VentureIntoTheDungeonEffect())
                .addHint(CurrentDungeonHint.instance));

        // Whenever Barrowin of Clan Undurr attacks, return up to one creature card with mana value 3 or less from your graveyard to the battlefield if you've completed a dungeon.
        Ability ability = new AttacksTriggeredAbility(new ConditionalOneShotEffect(
                new OneShotNonTargetEffect(new ReturnFromGraveyardToBattlefieldTargetEffect()
                        .setText("return up to one creature card with mana value 3 or less from your graveyard to the battlefield"),
                    new TargetCardInYourGraveyard(0, 1, filter, true)),
                CompletedDungeonCondition.instance
        ).withConditionTextAtEnd(true));
        this.addAbility(ability.addHint(CompletedDungeonCondition.getHint()), new CompletedDungeonWatcher());
    }

    private BarrowinOfClanUndurr(final BarrowinOfClanUndurr card) {
        super(card);
    }

    @Override
    public BarrowinOfClanUndurr copy() {
        return new BarrowinOfClanUndurr(this);
    }
}

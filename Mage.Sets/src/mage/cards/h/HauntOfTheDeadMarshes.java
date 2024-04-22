package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HauntOfTheDeadMarshes extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("you control a legendary creature");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);
    private static final Hint hint = new ConditionHint(condition, "You control a legendary creature");

    public HauntOfTheDeadMarshes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.ELF);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Haunt of the Dead Marshes enters the battlefield, scry 1.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ScryEffect(1, false)));

        // {2}{B}: Return Haunt of the Dead Marshes from your graveyard to the battlefield tapped. Activate only if you control a legendary creature.
        this.addAbility(new ConditionalActivatedAbility(
                Zone.GRAVEYARD, new ReturnSourceFromGraveyardToBattlefieldEffect(true, false),
                new ManaCostsImpl<>("{2}{B}"), condition
        ).addHint(hint));
    }

    private HauntOfTheDeadMarshes(final HauntOfTheDeadMarshes card) {
        super(card);
    }

    @Override
    public HauntOfTheDeadMarshes copy() {
        return new HauntOfTheDeadMarshes(this);
    }
}

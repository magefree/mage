package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.ControlYourCommanderCondition;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.effects.common.MillThenPutInHandEffect;
import mage.abilities.hint.common.MonarchHint;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class TChakaVenerableKing extends CardImpl {

    private static final FilterCard filter = new FilterCard("an artifact or land card");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
        filter.add(CardType.LAND.getPredicate());
    }

    public TChakaVenerableKing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When T'Chaka enters, mill three cards, then you may put an artifact or land card from among the milled cards into your hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MillThenPutInHandEffect(3, filter)));

        // {3}, Exile this card from your graveyard: You become the monarch. Activate only if you control your commander.
        Ability ability = new ActivateIfConditionActivatedAbility(
            Zone.GRAVEYARD,
            new BecomesMonarchSourceEffect(),
            new ManaCostsImpl<>("{3}"),
            ControlYourCommanderCondition.instance
        ).addHint(MonarchHint.instance);
        ability.addCost(new ExileSourceFromGraveCost());
        this.addAbility(ability);
    }

    private TChakaVenerableKing(final TChakaVenerableKing card) {
        super(card);
    }

    @Override
    public TChakaVenerableKing copy() {
        return new TChakaVenerableKing(this);
    }
}

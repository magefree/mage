package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.ExploreSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RiverHeraldScout extends CardImpl {

    public RiverHeraldScout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // When River Herald Scout enters the battlefield, it explores.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ExploreSourceEffect()));
    }

    private RiverHeraldScout(final RiverHeraldScout card) {
        super(card);
    }

    @Override
    public RiverHeraldScout copy() {
        return new RiverHeraldScout(this);
    }
}

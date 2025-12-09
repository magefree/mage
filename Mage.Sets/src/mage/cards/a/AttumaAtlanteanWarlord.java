package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.AttacksPlayerWithCreaturesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AttumaAtlanteanWarlord extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent(SubType.MERFOLK);

    public AttumaAtlanteanWarlord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Other Merfolk you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostAllEffect(
                1, 1, Duration.WhileOnBattlefield, filter, true
        )));

        // Whenever one or more Merfolk you control attack a player, draw a card.
        this.addAbility(new AttacksPlayerWithCreaturesTriggeredAbility(
                new DrawCardSourceControllerEffect(1), filter, SetTargetPointer.NONE
        ));
    }

    private AttumaAtlanteanWarlord(final AttumaAtlanteanWarlord card) {
        super(card);
    }

    @Override
    public AttumaAtlanteanWarlord copy() {
        return new AttumaAtlanteanWarlord(this);
    }
}

package mage.cards.h;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HedgeMaze extends CardImpl {

    public HedgeMaze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.FOREST);
        this.subtype.add(SubType.ISLAND);

        // ({T}: Add {G} or {U}.)
        this.addAbility(new GreenManaAbility());
        this.addAbility(new BlueManaAbility());

        // Hedge Maze enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When Hedge Maze enters the battlefield, surveil 1.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SurveilEffect(1, false)));
    }

    private HedgeMaze(final HedgeMaze card) {
        super(card);
    }

    @Override
    public HedgeMaze copy() {
        return new HedgeMaze(this);
    }
}

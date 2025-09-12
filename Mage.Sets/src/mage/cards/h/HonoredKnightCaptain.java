package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.game.permanent.token.HumanSoldierToken;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HonoredKnightCaptain extends CardImpl {

    private static final FilterCard filter = new FilterCard(SubType.EQUIPMENT);

    public HonoredKnightCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When this creature enters, create a 1/1 white Human Soldier creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new HumanSoldierToken())));

        // {4}{W}{W}, Sacrifice this creature: Search your library for an Equipment card, put it onto the battlefield, then shuffle.
        Ability ability = new SimpleActivatedAbility(
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter)), new ManaCostsImpl<>("{4}{W}{W}")
        );
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private HonoredKnightCaptain(final HonoredKnightCaptain card) {
        super(card);
    }

    @Override
    public HonoredKnightCaptain copy() {
        return new HonoredKnightCaptain(this);
    }
}

package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ruleModifying.PlayLandsFromGraveyardControllerEffect;
import mage.abilities.keyword.LandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterLandCard;
import mage.game.permanent.token.HazezonTamarSandWarriorToken;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class HazezonShaperOfSand extends CardImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("Desert");
    private static final FilterLandCard filter2 = new FilterLandCard("Desert lands");
    private static final FilterControlledLandPermanent filter3 = new FilterControlledLandPermanent("a Desert");

    static {
        filter.add(SubType.DESERT.getPredicate());
        filter2.add(SubType.DESERT.getPredicate());
        filter3.add(SubType.DESERT.getPredicate());
    }

    public HazezonShaperOfSand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{G}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Desertwalk
        this.addAbility(new LandwalkAbility(filter));

        // You may play Desert lands from your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PlayLandsFromGraveyardControllerEffect(filter2)));

        // Whenever a Desert enters the battlefield under your control create two 1/1 red, green, and white Sand Warrior creature tokens.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new CreateTokenEffect(
                new HazezonTamarSandWarriorToken(), 2), filter3
        ));
    }

    private HazezonShaperOfSand(final HazezonShaperOfSand card) {
        super(card);
    }

    @Override
    public HazezonShaperOfSand copy() {
        return new HazezonShaperOfSand(this);
    }
}
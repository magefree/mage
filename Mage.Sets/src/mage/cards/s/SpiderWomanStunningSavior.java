package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PermanentsEnterBattlefieldTappedEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpiderWomanStunningSavior extends CardImpl {

    public SpiderWomanStunningSavior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W/U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIDER);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Venom Blast -- Artifacts and creatures your opponents control enter tapped.
        this.addAbility(new SimpleStaticAbility(new PermanentsEnterBattlefieldTappedEffect(
                StaticFilters.FILTER_OPPONENTS_PERMANENT_ARTIFACT_OR_CREATURE
        ).setText("artifacts and creatures your opponents control enter tapped")));
    }

    private SpiderWomanStunningSavior(final SpiderWomanStunningSavior card) {
        super(card);
    }

    @Override
    public SpiderWomanStunningSavior copy() {
        return new SpiderWomanStunningSavior(this);
    }
}

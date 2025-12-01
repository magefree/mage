package mage.cards.j;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.DistributeCountersEffect;
import mage.abilities.keyword.CraftAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanentAmount;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JadeSeedstones extends TransformingDoubleFacedCard {

    public JadeSeedstones(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "{3}{G}",
                "Jadeheart Attendant",
                new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, new SubType[]{SubType.GOLEM}, "G");

        // Jade Seedstones

        // When Jade Seedstones enters the battlefield, distribute three +1/+1 counters among one, two, or three target creatures you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DistributeCountersEffect());
        ability.addTarget(new TargetCreaturePermanentAmount(3, StaticFilters.FILTER_CONTROLLED_CREATURES));
        this.getLeftHalfCard().addAbility(ability);

        // Craft with creature {5}{G}{G}
        this.getLeftHalfCard().addAbility(new CraftAbility(
                "{5}{G}{G}", "creature", "another creature you control or a creature card in your graveyard", CardType.CREATURE.getPredicate())
        );

        // Jadeheart Attendant
        this.getRightHalfCard().setPT(7, 7);

        // When Jadeheart Attendant enters the battlefield, you gain life equal to the mana value of the exiled card used to craft it.
        this.getRightHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(JadeheartAttendantValue.instance)
                .setText("you gain life equal to the mana value of the exiled card used to craft it")));
    }

    private JadeSeedstones(final JadeSeedstones card) {
        super(card);
    }

    @Override
    public JadeSeedstones copy() {
        return new JadeSeedstones(this);
    }
}

enum JadeheartAttendantValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        ExileZone exileZone = game
                .getExile()
                .getExileZone(CardUtil.getExileZoneId(game, sourceAbility, -2));
        return exileZone != null
                ? exileZone
                .getCards(game)
                .stream()
                .mapToInt(MageObject::getManaValue)
                .sum()
                : 0;
    }

    @Override
    public JadeheartAttendantValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "1";
    }
}


package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.permanent.token.TokenImpl;

/**
 *
 * @author Plopman
 */
public final class HauntedPlateMail extends CardImpl {

    public HauntedPlateMail(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +4/+4.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(4, 4)));
        // {0}: Until end of turn, Haunted Plate Mail becomes a 4/4 Spirit artifact creature that's no longer an Equipment. Activate this ability only if you control no creatures.
        Ability ability = new ConditionalActivatedAbility(
                Zone.BATTLEFIELD,
                new BecomesCreatureSourceEffect(new HauntedPlateMailToken(), "", Duration.EndOfTurn),
                new ManaCostsImpl<>("{0}"),
                new PermanentsOnTheBattlefieldCondition(StaticFilters.FILTER_PERMANENT_CREATURE, ComparisonType.EQUAL_TO, 0),
                "{0}: Until end of turn, Haunted Plate Mail becomes a 4/4 Spirit artifact creature that's no longer an Equipment. Activate only if you control no creatures.");
        this.addAbility(ability);
        // Equip {4}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new ManaCostsImpl<>("{4}")));
    }

    private HauntedPlateMail(final HauntedPlateMail card) {
        super(card);
    }

    @Override
    public HauntedPlateMail copy() {
        return new HauntedPlateMail(this);
    }
}

class HauntedPlateMailToken extends TokenImpl {

    public HauntedPlateMailToken() {
        super("Spirit", "4/4 Spirit artifact creature that's no longer an Equipment");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SPIRIT);
        power = new MageInt(4);
        toughness = new MageInt(4);
    }
    public HauntedPlateMailToken(final HauntedPlateMailToken token) {
        super(token);
    }

    public HauntedPlateMailToken copy() {
        return new HauntedPlateMailToken(this);
    }
}

package mage.cards.d;

import mage.MageInt;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.common.LandsYouControlHint;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.common.FilterLandPermanent;
import mage.game.permanent.token.DragonToken2;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class DragonmasterOutcast extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(new FilterLandPermanent("you control six or more lands"), ComparisonType.MORE_THAN, 5);

    public DragonmasterOutcast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of your upkeep, if you control six or more lands, create a 5/5 red Dragon creature token with flying.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new CreateTokenEffect(new DragonToken2(), 1))
                .withInterveningIf(condition).addHint(LandsYouControlHint.instance));
    }

    private DragonmasterOutcast(final DragonmasterOutcast card) {
        super(card);
    }

    @Override
    public DragonmasterOutcast copy() {
        return new DragonmasterOutcast(this);
    }
}

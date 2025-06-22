package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTargetAnyTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.ThatPlayerControlsTargetAdjuster;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class ScalelordReckoner extends CardImpl {

    private static final FilterPermanent filterDragon = new FilterControlledPermanent(SubType.DRAGON, "a Dragon you control");
    private static final FilterPermanent filterTarget = new FilterNonlandPermanent("nonland permanent that player controls");

    public ScalelordReckoner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a Dragon you control becomes the target of a spell or ability an opponent controls, destroy target nonland permanent that player controls.
        Ability ability = new BecomesTargetAnyTriggeredAbility(new DestroyTargetEffect(), filterDragon, StaticFilters.FILTER_SPELL_OR_ABILITY_OPPONENTS, SetTargetPointer.PLAYER, false);
        ability.addTarget(new TargetPermanent(filterTarget));
        ability.setTargetAdjuster(new ThatPlayerControlsTargetAdjuster());
        this.addAbility(ability);
    }

    private ScalelordReckoner(final ScalelordReckoner card) {
        super(card);
    }

    @Override
    public ScalelordReckoner copy() {
        return new ScalelordReckoner(this);
    }
}

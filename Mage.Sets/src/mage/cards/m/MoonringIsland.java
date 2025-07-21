package mage.cards.m;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.effects.common.LookLibraryTopCardTargetPlayerEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class MoonringIsland extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("if you control two or more blue permanents");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 1);

    public MoonringIsland(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        this.subtype.add(SubType.ISLAND);

        // <i>({tap}: Add {U}.)</i>
        this.addAbility(new BlueManaAbility());

        // Moonring Island enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {U}, {tap}: Look at the top card of target player's library. Activate this ability only if you control two or more blue permanents.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new LookLibraryTopCardTargetPlayerEffect(), new ManaCostsImpl<>("{U}"), condition
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private MoonringIsland(final MoonringIsland card) {
        super(card);
    }

    @Override
    public MoonringIsland copy() {
        return new MoonringIsland(this);
    }
}

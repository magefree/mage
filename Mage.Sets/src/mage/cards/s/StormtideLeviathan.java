package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantAttackAllEffect;
import mage.abilities.effects.common.continuous.AddBasicLandTypeAllLandsEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IslandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class StormtideLeviathan extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("Creatures without flying or islandwalk");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
        filter.add(Predicates.not(new AbilityPredicate(IslandwalkAbility.class)));
    }

    public StormtideLeviathan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}{U}");
        this.subtype.add(SubType.LEVIATHAN);

        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Islandwalk (This creature can't be blocked as long as defending player controls an Island.)
        this.addAbility(new IslandwalkAbility());

        // All lands are Islands in addition to their other types.
        this.addAbility(new SimpleStaticAbility(new AddBasicLandTypeAllLandsEffect(SubType.ISLAND)
                .setText("all lands are Islands in addition to their other types")));

        // Creatures without flying or islandwalk can't attack.
        this.addAbility(new SimpleStaticAbility(new CantAttackAllEffect(Duration.WhileOnBattlefield, filter)));
    }

    private StormtideLeviathan(final StormtideLeviathan card) {
        super(card);
    }

    @Override
    public StormtideLeviathan copy() {
        return new StormtideLeviathan(this);
    }

}

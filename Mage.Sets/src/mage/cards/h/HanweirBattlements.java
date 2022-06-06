
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.MeldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.MeldEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class HanweirBattlements extends CardImpl {

    public HanweirBattlements(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {R},{T}: Target creature gains haste until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{R}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // {3}{R}{R},{T}: If you both own and control Hanweir Battlements and a creature named Hanweir Garrison, exile them, then meld them into Hanweir, the Writhing Township.
        ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD,
                new MeldEffect("Hanweir Garrison",
                        new HanweirTheWrithingTownship(ownerId, new CardSetInfo("Hanweir, the Writhing Township", "EMN", "130", Rarity.RARE))),
                new ManaCostsImpl<>("{3}{R}{R}"), new MeldCondition("Hanweir Garrison"),
                "{3}{R}{R}, {T}: If you both own and control {this} and a creature named Hanweir Garrison, exile them, then meld them into Hanweir, the Writhing Township.");
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private HanweirBattlements(final HanweirBattlements card) {
        super(card);
    }

    @Override
    public HanweirBattlements copy() {
        return new HanweirBattlements(this);
    }
}

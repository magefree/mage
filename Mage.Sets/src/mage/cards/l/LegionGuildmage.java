package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class LegionGuildmage extends CardImpl {

    public LegionGuildmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {5}{R}, {T}: Legion Guildmage deals 3 damage to each opponent.
        Ability ability = new SimpleActivatedAbility(
                new DamagePlayersEffect(3, TargetController.OPPONENT),
                new ManaCostsImpl<>("{5}{R}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {2}{W}, {T}: Tap another target creature.
        ability = new SimpleActivatedAbility(
                new TapTargetEffect("tap another target creature"),
                new ManaCostsImpl<>("{2}{W}")
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE));
        this.addAbility(ability);
    }

    private LegionGuildmage(final LegionGuildmage card) {
        super(card);
    }

    @Override
    public LegionGuildmage copy() {
        return new LegionGuildmage(this);
    }
}

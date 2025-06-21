package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class RazorgrassInvoker extends CardImpl {

    public RazorgrassInvoker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // {8}: Razorgrass Invoker and up to one other target creature each get +3/+3 until end of turn.
        Ability ability = new SimpleActivatedAbility(new BoostSourceEffect(3, 3, Duration.EndOfTurn).setText("{this}"), new ManaCostsImpl<>("{8}"));
        ability.addEffect(new BoostTargetEffect(3, 3, Duration.EndOfTurn).setText("and up to one other target creature each get +3/+3 until end of turn"));
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_ANOTHER_TARGET_CREATURE));
        this.addAbility(ability);
    }

    private RazorgrassInvoker(final RazorgrassInvoker card) {
        super(card);
    }

    @Override
    public RazorgrassInvoker copy() {
        return new RazorgrassInvoker(this);
    }
}

package mage.cards.z;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.SwampwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author KholdFuzion
 */
public final class ZombieMaster extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent(SubType.ZOMBIE, "Zombie creatures");
    private static final FilterPermanent filter2 = new FilterPermanent(SubType.ZOMBIE, "Zombies");

    public ZombieMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Other Zombie creatures have swampwalk.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                new SwampwalkAbility(), Duration.WhileOnBattlefield, filter, true
        )));

        // Other Zombies have "{B}: Regenerate this permanent."
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(new SimpleActivatedAbility(
                new RegenerateSourceEffect("this permanent"), new ManaCostsImpl<>("{B}")
        ), Duration.WhileOnBattlefield, filter2, true)));
    }

    private ZombieMaster(final ZombieMaster card) {
        super(card);
    }

    @Override
    public ZombieMaster copy() {
        return new ZombieMaster(this);
    }
}
